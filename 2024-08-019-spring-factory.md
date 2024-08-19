---
layout: single
title: 2024/08/19 Spring-Factory
---
#### MessageObject.java Interface

```java
package xyz.itwill02.factory;

// Interface that must be inherited by classes created and provided by the Factory class
// Classes that implement this interface must override and declare all abstract methods defined in the interface
// => Provides method implementation rules for classes inheriting the interface - serves as a work specification
// When a reference variable is created as an interface type, it can only hold objects of classes that implement the interface
// => When calling abstract methods using a reference variable created as an interface, the overridden methods of the child class
// objects are called due to implicit object type casting - polymorphism due to method overriding: reduces coupling between classes
public interface MessageObject {
    String getMessage();
}
```



#### HelloMessageObject.java Class

```java
package xyz.itwill02.factory;

// Class to be created and provided by the Factory class
// => It is recommended to implement this class by extending an interface
public class HelloMessageObject implements MessageObject {
    @Override
    public String getMessage() {
        return "Hello!!!";
    }
}
```

#### HiMessageObject.java Class

```java
package xyz.itwill02.factory;

public class HiMessageObject implements MessageObject {
	@Override
	public String getMessage() {
		return "Hi!!!";
	}
}
```

#### MessageObjectFactory.java Class
```java
package xyz.itwill02.factory;

// Class written using the Factory design pattern - Factory class
// => A class for creating and providing objects needed for program development
// => Provides IoC (Inversion of Control) functionality
public class MessageObjectFactory {
    // Constant fields for distinguishing objects provided by the Factory class
    public static final int HELLO_MSG = 1;
    public static final int HI_MSG = 2;
    
    // Static method for creating and returning objects of classes that implement the interface
    // => Provides different objects based on the value passed as a parameter
    public static MessageObject getMessageObject(int message) {
        MessageObject object = null;
        switch (message) {
            case HELLO_MSG:
                object = new HelloMessageObject();
                break;
            case HI_MSG:
                object = new HiMessageObject();
                break;
        }
        return object;
    }
}
```


#### MessagePrintObject.java Class

```java
package xyz.itwill02.factory;

public class MessagePrintObject {
    public void messagePrint() {
        // Creating the object directly and calling the method - high coupling between objects reduces maintainability
        // MessageObject object = new HelloMessageObject();
        
        // Obtaining the necessary object for program execution from the Factory class and calling the method
        // => Using Inversion of Control (IoC) to reduce coupling between objects, thereby increasing maintainability
        // MessageObject object = MessageObjectFactory.getMessageObject(MessageObjectFactory.HELLO_MSG);
        MessageObject object = MessageObjectFactory.getMessageObject(MessageObjectFactory.HI_MSG);
        
        String message = object.getMessage();
        System.out.println("message = " + message);
    }
}
```



#### MessagePrintApp.java Class
```java

package xyz.itwill02.factory;

public class MessagePrintApp {
	public static void main(String[] args) {
		MessagePrintObject print=new MessagePrintObject();
		print.messagePrint();
	}
}
```