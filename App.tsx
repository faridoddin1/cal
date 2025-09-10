
import React, { useState } from 'react';
import {
  SafeAreaView,
  StyleSheet,
  Text,
  View,
  TouchableOpacity,
} from 'react-native';

const App = () => {
  const [display, setDisplay] = useState('0');
  const [calculation, setCalculation] = useState('');

  const handleNumberPress = (num) => {
    setDisplay(display === '0' ? String(num) : display + num);
  };

  const handleOperatorPress = (op) => {
    setCalculation(display + op);
    setDisplay('0');
  };

  const handleClearPress = () => {
    setDisplay('0');
    setCalculation('');
  };

  const handleEqualsPress = () => {
    try {
      const result = eval(calculation + display);
      setDisplay(String(result));
      setCalculation('');
    } catch (e) {
      setDisplay('Error');
    }
  };

  const handleDecimalPress = () => {
    if (!display.includes('.')) {
      setDisplay(display + '.');
    }
  };

  const handlePlusMinusPress = () => {
    setDisplay(String(parseFloat(display) * -1));
  };

  const handlePercentagePress = () => {
    setDisplay(String(parseFloat(display) / 100));
  };

  const renderButton = (text, onPress, buttonStyle, textStyle) => (
    <TouchableOpacity onPress={onPress} style={[styles.button, buttonStyle]}>
      <Text style={[styles.buttonText, textStyle]}>{text}</Text>
    </TouchableOpacity>
  );

  return (
    <SafeAreaView style={styles.container}>
      <View style={styles.header}>
        <TouchableOpacity>
          <Text style={styles.headerIcon}>☰</Text>
        </TouchableOpacity>
        <Text style={styles.headerTitle}>Gcalculator</Text>
        <TouchableOpacity>
          <Text style={styles.headerIcon}>🕓</Text>
        </TouchableOpacity>
      </View>
      <View style={styles.displayContainer}>
        <Text style={styles.calculationText}>{calculation}</Text>
        <Text style={styles.displayText}>{display}</Text>
      </View>
      <View style={styles.buttonsContainer}>
        <View style={styles.row}>
          {renderButton('AC', handleClearPress, styles.lightGreenButton, styles.blackText)}
          {renderButton('±', handlePlusMinusPress, styles.lightGreenButton, styles.blackText)}
          {renderButton('%', handlePercentagePress, styles.lightGreenButton, styles.blackText)}
          {renderButton('÷', () => handleOperatorPress('/'), styles.limeGreenButton)}
        </View>
        <View style={styles.row}>
          {renderButton('7', () => handleNumberPress(7), styles.whiteButton)}
          {renderButton('8', () => handleNumberPress(8), styles.whiteButton)}
          {renderButton('9', () => handleNumberPress(9), styles.whiteButton)}
          {renderButton('×', () => handleOperatorPress('*'), styles.limeGreenButton)}
        </View>
        <View style={styles.row}>
          {renderButton('4', () => handleNumberPress(4), styles.whiteButton)}
          {renderButton('5', () => handleNumberPress(5), styles.whiteButton)}
          {renderButton('6', () => handleNumberPress(6), styles.whiteButton)}
          {renderButton('-', () => handleOperatorPress('-'), styles.limeGreenButton)}
        </View>
        <View style={styles.row}>
          {renderButton('1', () => handleNumberPress(1), styles.whiteButton)}
          {renderButton('2', () => handleNumberPress(2), styles.whiteButton)}
          {renderButton('3', () => handleNumberPress(3), styles.whiteButton)}
          {renderButton('+', () => handleOperatorPress('+'), styles.limeGreenButton)}
        </View>
        <View style={styles.row}>
          {renderButton('0', () => handleNumberPress(0), [styles.zeroButton, styles.whiteButton])}
          {renderButton('.', handleDecimalPress, styles.whiteButton)}
          {renderButton('=', handleEqualsPress, styles.limeGreenButton)}
        </View>
      </View>
    </SafeAreaView>
  );
};

const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: '#f0f0e0',
  },
  header: {
    flexDirection: 'row',
    justifyContent: 'space-between',
    alignItems: 'center',
    paddingHorizontal: 20,
    paddingTop: 20,
  },
  headerIcon: {
    fontSize: 24,
  },
  headerTitle: {
    fontSize: 20,
    fontWeight: 'bold',
  },
  displayContainer: {
    flex: 2,
    justifyContent: 'flex-end',
    alignItems: 'flex-end',
    padding: 20,
  },
  calculationText: {
    fontSize: 24,
    color: '#888',
  },
  displayText: {
    fontSize: 80,
    color: '#000',
  },
  buttonsContainer: {
    flex: 3,
    padding: 10,
  },
  row: {
    flexDirection: 'row',
    justifyContent: 'space-around',
    marginBottom: 10,
  },
  button: {
    borderRadius: 50,
    width: 80,
    height: 80,
    justifyContent: 'center',
    alignItems: 'center',
  },
  buttonText: {
    fontSize: 32,
    color: '#000',
  },
  lightGreenButton: {
    backgroundColor: '#d0e0d0',
  },
  limeGreenButton: {
    backgroundColor: '#90ee90',
  },
  whiteButton: {
    backgroundColor: '#fff',
  },
  zeroButton: {
    width: 170,
  },
  blackText: {
    color: '#000',
  },
});

export default App;
