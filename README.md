# Project Title

**Location Sharing (tmp)**

Yi-Yang Lin

Haohan Guo

**Instructions**

- This is the template of your final project report.  As this document will be constantly updated during the semester, please enable the “track changes” in your doc. Or if you prefer to use the md file, we can also see the change in the commit history.
- Please name your report as CS683\_<Last Name><First Name>\_<ProjectTitle>. It can be either a PDF or Word document. 
- Make sure to push all your code into your github repository and submit your source code in a zip file named CS683\_<Last Name><First Name>\_<ProjectTitle>.zip on blackboard. 
- Please provide your feedback in the “Add comments” section when submitting your lab report. Thanks! 


**Overview**

**Related Work**

**Requirement Analysis and Testing**

**Design and Implementation**

**Project Structure**

**Timeline**

**Future Work (Optional)**

**Project Demo Links**



## Overview
(*This section should give an overview of your project. It should include the motivation, the purpose and the potential users of the proposed application. This section should be completed in iteration 0 as part of your proposal. It can be modified in later iterations.* )


This project is a real-time location sharing system among friends. In this system, the map provides location sharing which allows a group of people to know about each one’s specific location on a sharing map. By using the app users can find the location of their friends on the map and can communicate with each other with messages.

For instance, a group of friends, approximately 5 ~ 10 people, entering an amusement park and some people may want to split the big group into small groups; some may want to be independent and have different rides alone. Therefore, this app indicates the relative or absolute distance between each one and tracks friends’ distance to have a better trip plan.


## Related Work

A product with similar features on the market is *WeChat*, which has two main functions, namely "Send Location" and "Share Live Location" buttons. "Send Location" shows your location information. The "Share Live Location" will display everyone's location information. When we click "Share Live Location", our own location will be displayed immediately and will change dynamically as we move around.



Compared to *WeChat*'s location sharing, our differences are: 1. we don't need to add friends, we can see all users who use the app for real-time location sharing in this area on the map. 2. users can make a public destination and all related users will receive the system's automatic navigation to that destination

Some apps that are similar in the market - 

*Glympse* - share real-time location using GPS tracking, with any of family, friends or co-workers, it can set sharing time duration to others and send customized or default messages to others.



*Find My Kids: location tracker* - a family GPS location tracker designed for child safety and parental controls. You can connect a GPS kids watch or install the app on your child's phone to discreetly track their geolocation.



*isharing* - find my friends app lets user’s family be in touch and stay connected with family members anytime. The family sharing app provides a real-time location sharing service allowing families to privately share their location information and communicate with each other, reducing anxiety around the whereabouts of their family members with alerting messages.



## Requirement Analysis and Testing
(*This section should clearly describe all features/requirements that you plan to implement or have implemented for your application. You should separate them into three categories: essential, desirable and optional.*



|*Title*|*Login or signup user account or enter as a guest (essential)*|
| --- | --- |
|*Description*|*Users are able to login as a guest or registered member*|
|*Mockups*||
|*Acceptance tests*||
|*Test Results*||
|*Status*||







|*Title*|*Invitation with QR codes or links (essential)*|
| --- | --- |
|*Description*|*Each user can generate exclusive account code to invite or be invited into a limited range map*|
|*Mockups*||
|*Acceptance tests*||
|*Test Results*||
|*Status*||



|*Title*|*Location sharing (essential)*|
| --- | --- |
|*Description*|*By initiating location sharing, you can see where you and your friends are on the map*|
|*Mockups*||
|*Acceptance tests*||
|*Test Results*||
|*Status*||


|*Title*|*Send messages (desirable)*|
| --- | --- |
|*Description*|*Send messages by clicking on the avatar or adding friends*|
|*Mockups*||
|*Acceptance tests*||
|*Test Results*||
|*Status*||


|*Title*|*Map navigation (desirable)*|
| --- | --- |
|*Description*|*By developing a destination, the system will give map navigation for two or more people to reach a common destination*|
|*Mockups*||
|*Acceptance tests*||
|*Test Results*||
|*Status*||


|*Title*|*Hover user location spot on map to show friend’s user profile and information (desirable)*|
| --- | --- |
|*Description*|*When user hover other user’s point on map, it should pop-up user profile information and provide functions with buttons*|
|*Mockups*||
|*Acceptance tests*||
|*Test Results*||
|*Status*||


|*Title*|*Tracking user history route (optional)*|
| --- | --- |
|*Description*|*User’s route history will be stored in cloud backend and can be shown in app tab whenever user wants to check*|
|*Mockups*||
|*Acceptance tests*||
|*Test Results*||
|*Status*||





*In Iteration 0 (project planning phase), this section should contain most essential features, some desirable features and possibly a few optional features if you want. Each feature listed in this section should have a title and a brief description, preferably using the user story template “As (a role)… I want (some feature), so that (value)...” . Each essential feature should also have at least one acceptance test, and one or multiple mockups if applicable.*

*In later iterations (iteration 1 to 5), this section will be constantly updated. Highlight each feature/requirement that you are working on, you should also provide an update to the status and some test results if it is completed or partially completed.* )

## Design and Implementation 
(*This section should describe the basic architecture (e.g. MVC, or MVVM) and your detailed design and implementation.  This section may contain the following aspects:*

- *Basic architecture*
- *UI design and implementation*
  - *Activities, fragments, special widgets, etc*
- *Other android features* 
  - *Service, sensors, animations, etc*
- *Third party APIs*
- *Data Design and implementation* 
  - *Database schema, data storage* 
- *Algorithms*
- *…*

*You can provide some brief description as well as supporting evidence, such as sample code, log info, or screenshots. You want to specify mapped requirements and files/classes in your project.* 

*This section is not needed for iteration 0. In later iterations, you shall highlight the work you are doing  in the current iteration.* )


## Project Structure
(*Please provide a screenshot(s) of your current project structure, which should show all the packages, kotlin/java files and resource files in your project. You should also highlight any files/packages you have changed, added/deleted in this iteration compared with the previous iteration. This is not needed for iteration 0*)

## Timeline

*(Please provide  a summary of the requirements implemented and Android/third party components used in the past and current iterations, and the plan in the future iteration.)*

|Iteration | Application Requirements (Eseential/Desirable/Optional) | Android Components and Features| member 1 contribution/tasks| member 2 contribution/tasks|
|---|---|---|---|---|
|0|<p>Listed app overview and potential features </p><p>Functions are listed and defined requirements</p> |None |<p>Communicate and discuss to determine the topic, write iteration 0, and summarize</p> |<p>Communicate and discuss to determine the topic, write iteration 0, and summarize</p> |
|1| <p>Complete the essential part of the function, including user login, scan code to share the link, share location</p>|<p>Alibaba Cloud Short Message Service</p> | | |
|2| | | | |


## Future Work (Optional)
*(This section can describe possible future works. Particularly the requirements you planned but didn’t get time to implement, and possible Android components or features to implement them. 
This section is optional, and you can include this section in the final iteration if you want.)*

    
## Project Demo Links
*(For on campus students, we will have project presentations in class. For online students, you are required to submit a video of your project presentation which includes a demo of your app. You can use Kaltura to make the video and then submit it on blackboard. Please check the following link for the details of using Kaltura to make and submit videos on blackboard. You can also use other video tools and upload your video to youtube if you like: https://onlinecampus.bu.edu/bbcswebdav/courses/00cwr_odeelements/metcs/cs_Kaltura.htm  )*


## References

<https://glympse.com/>

<https://findmykids.org/>

<https://isharingsoft.com/>
