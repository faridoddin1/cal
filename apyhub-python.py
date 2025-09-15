import os
import tempfile
import requests
from pyrogram import Client, filters
from pyrogram.types import CallbackQuery
import os

API_ID = os.environ.get("API_ID")
API_HASH = os.environ.get("API_HASH")
API_TOKEN = os.environ.get("API_TOKEN")

# ApyHub API configuration
APYHUB_TOKEN = os.environ.get("APYHUB_TOKEN")
APYHUB_API_URL = 'https://api.apyhub.com/compress/video/file'

app = Client("bot", api_id=API_ID, api_hash=API_HASH, bot_token=API_TOKEN)

@app.on_message(filters.command("start"))
def start(client, message):
    message.reply_text("Send your video files for compression")

@app.on_message(filters.video | filters.document)
def handle_media(client, message):
    # If document, check if mimetype is video/mp4
    if message.document:
        if not message.document.mime_type or "video" not in message.document.mime_type:
            message.reply_text("Please send a valid video file.")
            return
        file_id = message.document.file_id
        file_name = message.document.file_name or "video.mp4"
    else:
        file_id = message.video.file_id
        file_name = "video.mp4"
    
    # Send processing message
    processing_msg = message.reply_text("Processing your video... Please wait.")
    
    try:
        # Download the file
        downloaded_file = client.download_media(file_id)
        
        # Prepare ApyHub API request
        headers = {
            'apy-token': APYHUB_TOKEN,
        }
        
        # Create output filename
        base_name = os.path.splitext(file_name)[0]
        output_filename = f"{base_name}_compressed.mp4"
        
        params = {
            'output': output_filename,
        }
        
        # Open the downloaded file and send to ApyHub
        with open(downloaded_file, 'rb') as video_file:
            files = {
                'video': video_file,
                'compression_percentage': (None, '50'),  # 50% compression
            }
            
            response = requests.post(APYHUB_API_URL, params=params, headers=headers, files=files)
        
        if response.status_code == 200:
            # Save the compressed video to a temporary file
            with tempfile.NamedTemporaryFile(suffix='.mp4', delete=False) as temp_file:
                temp_file.write(response.content)
                temp_filename = temp_file.name
            
            # Send the compressed video back
            processing_msg.edit_text("Compression complete! Sending compressed video...")
            message.reply_video(
                temp_filename,
                caption=f"✅ Video compressed successfully!\n📁 Original: {file_name}\n🗜️ Compression: 50%"
            )
            
            # Clean up temporary file
            os.remove(temp_filename)
        else:
            processing_msg.edit_text(f"❌ Compression failed. Error: {response.status_code}")
            
    except requests.exceptions.RequestException as e:
        processing_msg.edit_text(f"❌ Network error occurred: {str(e)}")
    except Exception as e:
        processing_msg.edit_text(f"❌ An error occurred: {str(e)}")
    finally:
        # Clean up downloaded file
        if 'downloaded_file' in locals() and os.path.exists(downloaded_file):
            os.remove(downloaded_file)

if __name__ == "__main__":
    app.run()
