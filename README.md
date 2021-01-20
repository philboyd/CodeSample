# Code Challenge
This repo is to demonstrate my habits for creating android apps

# Architecture Overview
### Overall
This app utilizes clean architecture.

There are 3 main directories: presentation, domain, and data.

The presentation layer is responsible for rendering and view state.
The domain layer holds our business entities and use cases.
The data is used for storage and network calls.

The layers are circled around each other such that presentation > data > domain.
Meaning the presentation layer knows about data and domain, the data layer knows about domain, and
finally, the domain only knows about itself.


### Presentation Layer
In the Presentation Layer, I've used MVVM pattern to bind data to the view.

### Dependency Injection
In a larger app, I would leverage a tool/library such as Dagger2 to help coordinate dependency injection.
For this exercise, I used a container approach. 
