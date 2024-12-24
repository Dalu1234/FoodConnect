# FoodConnect: Volunteer Coordination System

FoodConnect is a volunteer management system designed for community food organizations, including food banks and food pantries. This application simplifies the process of volunteer sign-ups, tracking, and prioritization using a robust Java-based architecture.

## Features
- **Volunteer Management**: Register volunteers with specific availability, location, and donation preferences.
- **Organization Management**: Track volunteer needs and donation requirements for food banks and pantries.
- **GUI Integration**: User-friendly graphical interface for managing volunteers and organizations.
- **Prioritization**: Automatically prioritize volunteer placements based on organizational needs.
- **Inheritance Model**: Implements an inheritance-based design to manage food banks and pantries with shared and unique functionalities.

## Technologies Used
- **Programming Language**: Java
- **GUI Framework**: Swing
- **Data Management**: Text file-based persistence for organization and volunteer data
- **Algorithms**: Sorting and matching algorithms to optimize volunteer placements

## How to Use
1. Clone this repository:
    ```bash
    git clone https://github.com/yourusername/FoodConnect.git
    cd FoodConnect
    ```
2. Open the project in your Java IDE (e.g., Eclipse or IntelliJ IDEA).
3. Run the main class to start the application:
    - Ensure the `data` folder contains the `community_food_organizations.txt` and `volunteers.txt` files.
4. Use the GUI to:
    - Register new volunteers.
    - View and prioritize food organization needs.
    - Sign up volunteers based on their availability and preferences.

## File Structure
- **src**: Contains all source code files.
  - `CommunityFoodOrg.java`: Base class for all food organizations.
  - `FoodBank.java` and `FoodPantry.java`: Derived classes for specific organization types.
  - `Volunteer.java`: Handles volunteer attributes and matching logic.
  - `VolunteeringManager.java`: Manages the interaction between volunteers and organizations.
  - `DataManager.java`: Handles reading/writing organization and volunteer data.
  - `FoodConnectGUI.java`: GUI implementation.
- **data**: Stores text files for community organizations and volunteers.
  - `community_food_organizations.txt`
  - `volunteers.txt`
- **README.md**: This file.

## Future Enhancements
- Add database support for better scalability.
- Introduce geolocation APIs for dynamic distance calculations.
- Enhance the GUI with modern frameworks like JavaFX.

## License
This project is licensed under the MIT License. See the `LICENSE` file for details.

## Author
Chukwudalu Dumebi-Kachikwu - Passionate about software development and creating meaningful solutions for community-driven challenges.
