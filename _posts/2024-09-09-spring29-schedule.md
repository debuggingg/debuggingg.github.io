---
layout: single
title: 2024-09-09-spring29-schedule
---

#### servlet-context.xml
Schduler 
1. servlet-context -setup
2. spring-task.xsd - set task with namespace 


```xml
<!-- Register the scheduling-related class as a Spring Bean -->
<beans:bean class="xyz.itwill09.util.Scheduler" id="scheduler"/>

<!-- Add the task namespace to the Spring Bean Configuration File to enable the use of elements from the spring-task.xsd file -->
<!-- scheduler: An element that provides scheduling functionality -->
<!-- id attribute: Set the identifier for the scheduler element -->
<!-- pool-size attribute: Set the number of threads for implementing scheduling functionality -->
<task:scheduler id="baseScheduler" pool-size="10"/>

<!-- scheduled-tasks: Element to configure the objects to be executed by the scheduler element -->
<!-- scheduler attribute: Set the identifier of the scheduler (scheduler element) for providing scheduling functionality -->
<!-- scheduled: Element to configure the objects and methods to be executed by the scheduler -->
<!-- ref attribute: Set the Spring Bean identifier (beanName) of the object to be executed by the scheduler -->
<!-- method attribute: Set the name of the method to be called on the scheduling object -->
<!-- cron attribute: Set the date and time for executing the scheduling functionality -->
<!-- => Attribute value: seconds minutes hours day-of-month month day-of-week - pattern characters are allowed -->
<!-- 
<task:scheduled-tasks scheduler="baseScheduler">
    <task:scheduled ref="scheduler" method="autoUpdate" cron="1 * * * * *"/>
</task:scheduled-tasks>
-->

<!-- annotation-driven: Element to enable the use of scheduling-related annotations -->
<task:annotation-driven/>
```

### Explanation:

- **Scheduling Bean Registration**:
  - Registers a class (`Scheduler`) as a Spring Bean with the ID `scheduler`.

- **Task Namespace Configuration**:
  - Adds the `task` namespace to the Spring configuration to use scheduling elements.

- **Scheduler Configuration**:
  - Configures a scheduler (`baseScheduler`) with a thread pool size of 10.

- **Scheduled Tasks Configuration**:
  - Configures tasks to be scheduled.
  - `scheduler` attribute specifies which scheduler to use.
  - `scheduled` element defines the tasks to be executed, with attributes for the reference to the Spring Bean, method name, and cron expression for timing.

- **Annotation-Driven Scheduling**:
  - Enables the use of scheduling annotations (e.g., `@Scheduled`) in the Spring configuration.
#### Scheduler.java
```java
package xyz.itwill09.util;

import org.springframework.scheduling.annotation.Scheduled;

// Spring Scheduling: Provides functionality to automatically execute commands at specified dates and times
// => Enables automatic execution of tasks such as handling inactive accounts and sending periodic emails
// => Write a scheduling-related class, register it as a Spring Bean in the Spring Bean Configuration File (servlet-context.xml),
// and configure methods to be automatically called at specific dates and times
// => Use the @Scheduled annotation on methods in the scheduling-related class to schedule automatic method calls
public class Scheduler {
    //@Scheduled: Annotation that provides scheduling functionality to automatically call the method
    // cron attribute: Set the date and time for automatically calling the method
    // => Attribute value: seconds minutes hours day-of-month month day-of-week
    // => Day of week: 0 (Sunday) to 6 (Saturday) - [7] can be used instead of [0]
    //@Scheduled(cron = "1 * * * * *") // Every month, every day, every day of the week, every hour, every minute, at 1 second
    //@Scheduled(cron = "0 1 * * * *") // Every month, every day, every day of the week, every hour, at 1 minute and 0 seconds
    //@Scheduled(cron = "0 0 5 * * *") // Every month, every day, every day of the week, at 5 hours, 0 minutes, and 0 seconds
    //@Scheduled(cron = "0 0 5 1 * *") // January 1st, every year, at 5 hours, 0 minutes, and 0 seconds
    //@Scheduled(cron = "0 0 5 * * 1-5") // Every month, every day, Monday through Friday, at 5 hours, 0 minutes, and 0 seconds
    @Scheduled(cron = "0 0 5 1 1-12/3 *") // On the 1st day of every 3 months from January to December, at 5 hours, 0 minutes, and 0 seconds
    public void autoUpdate() {
        System.out.println("### Scheduler class's autoUpdate() method called ###");
    }
}
```

### Explanation:

- **Spring Scheduling**:
  - Provides functionality for automatic execution of tasks at specified dates and times.
  - Typical uses include handling inactive accounts and sending periodic emails.
  - Configure and register a scheduling-related class as a Spring Bean, and set up methods to be automatically invoked.

- **`@Scheduled` Annotation**:
  - Provides scheduling functionality to automatically call the method.
  - **`cron` attribute**: Specifies when the method should be called.
    - **Format**: `seconds minutes hours day-of-month month day-of-week`
    - **Day of the week**: `0` (Sunday) through `6` (Saturday); `7` can also be used for Sunday.
  
- **Cron Expressions Examples**:
  - `"1 * * * * *"`: Every second at 1 second of every minute.
  - `"0 1 * * * *"`: Every minute at 1 minute and 0 seconds.
  - `"0 0 5 * * *"`: Every day at 5 AM.
  - `"0 0 5 1 * *"`: January 1st at 5 AM.
  - `"0 0 5 * * 1-5"`: Weekdays (Monday to Friday) at 5 AM.
  - `"0 0 5 1 1-12/3 *"`: The 1st day of every 3 months from January to December at 5 AM.