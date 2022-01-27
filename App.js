import { StatusBar } from 'expo-status-bar';
import { StyleSheet, Text, View, TouchableOpacity, NativeModules } from 'react-native';

export default function App() {
  return (
    <View style={styles.container}>
      <Text>Open up App.js to start working on your app!</Text>
      <TouchableOpacity style = {{ padding: 10, backgroundColor: 'red', borderRadius: 20 }}
      onPress = {() => {
        NativeModules.ActivityStarter.navigateToExample()
      }}>
        <Text>Navigate to android native activity</Text>
      </TouchableOpacity>
    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: '#fff',
    alignItems: 'center',
    justifyContent: 'center',
  },
});
