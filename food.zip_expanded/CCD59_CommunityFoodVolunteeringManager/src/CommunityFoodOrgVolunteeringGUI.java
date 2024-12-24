import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.*;
/**
 * Class CommunityFoodOrgVolunteeringGUI
 * @author : Chukwudalu Dumebi-Kachikwu
 * @created 12/10/2024
 */
public class CommunityFoodOrgVolunteeringGUI {

    public ArrayList<Volunteer> volunteersList = new ArrayList<>();
    public ArrayList<CommunityFoodOrg> organizationsList = DataManager.readCommunityFoodOrgs("src/data/community_food_organizations.txt");

    public JFrame frmMainWindow;
    public JTextField txtFullName, txtAge, txtStartTime, txtEndTime, txtDistance;
    public JTextField txtLatitude, txtLongitude, txtDonation;
    public JCheckBox chkNeedsTransport;
    public JComboBox<String> cmbDayAvailable;
    public JButton btnAddVolunteer, btnSignUp;
    public JList<String> orgList;
    public DefaultListModel<String> orgListModel;
    public Volunteer currentVolunteer;
    /**
     * Constructor for CommunityFoodOrgVolunteeringGUI.
     * Sets up the main GUI window for managing community food organization volunteers.
     * Initializes all the GUI components including text fields, labels, buttons, and event listeners.
     * The GUI allows users to add volunteer details, view matching organizations, and sign up for volunteer opportunities.
     */
    public CommunityFoodOrgVolunteeringGUI() {
        frmMainWindow = new JFrame("Community Food Volunteering Manager");
        frmMainWindow.setSize(600, 400);
        frmMainWindow.setLayout(null);
        frmMainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Existing GUI Components
        JLabel lblFullName = new JLabel("Full Name:");
        lblFullName.setBounds(20, 20, 80, 25);
        frmMainWindow.add(lblFullName);

        txtFullName = new JTextField();
        txtFullName.setBounds(120, 20, 150, 25);
        frmMainWindow.add(txtFullName);

        JLabel lblAge = new JLabel("Age:");
        lblAge.setBounds(20, 60, 80, 25);
        frmMainWindow.add(lblAge);

        txtAge = new JTextField();
        txtAge.setBounds(120, 60, 150, 25);
        frmMainWindow.add(txtAge);

        JLabel lblDayAvailable = new JLabel("Day Available:");
        lblDayAvailable.setBounds(20, 100, 100, 25);
        frmMainWindow.add(lblDayAvailable);

        cmbDayAvailable = new JComboBox<>(new String[]{"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"});
        cmbDayAvailable.setBounds(120, 100, 150, 25);
        frmMainWindow.add(cmbDayAvailable);

        JLabel lblStartTime = new JLabel("Start Time (HH:MM):");
        lblStartTime.setBounds(20, 140, 120, 25);
        frmMainWindow.add(lblStartTime);

        txtStartTime = new JTextField();
        txtStartTime.setBounds(160, 140, 100, 25);
        frmMainWindow.add(txtStartTime);

        JLabel lblEndTime = new JLabel("End Time (HH:MM):");
        lblEndTime.setBounds(20, 180, 120, 25);
        frmMainWindow.add(lblEndTime);

        txtEndTime = new JTextField();
        txtEndTime.setBounds(160, 180, 100, 25);
        frmMainWindow.add(txtEndTime);

        JLabel lblDistance = new JLabel("Max Distance:");
        lblDistance.setBounds(20, 220, 120, 25);
        frmMainWindow.add(lblDistance);

        txtDistance = new JTextField();
        txtDistance.setBounds(160, 220, 100, 25);
        frmMainWindow.add(txtDistance);

        chkNeedsTransport = new JCheckBox("Needs Transportation");
        chkNeedsTransport.setBounds(20, 260, 200, 25);
        frmMainWindow.add(chkNeedsTransport);

        // New Input Fields for Latitude, Longitude, and Donation
        JLabel lblLatitude = new JLabel("Latitude:");
        lblLatitude.setBounds(300, 20, 80, 25);
        frmMainWindow.add(lblLatitude);

        txtLatitude = new JTextField();
        txtLatitude.setBounds(400, 20, 150, 25);
        frmMainWindow.add(txtLatitude);

        JLabel lblLongitude = new JLabel("Longitude:");
        lblLongitude.setBounds(300, 60, 80, 25);
        frmMainWindow.add(lblLongitude);

        txtLongitude = new JTextField();
        txtLongitude.setBounds(400, 60, 150, 25);
        frmMainWindow.add(txtLongitude);

        JLabel lblDonation = new JLabel("Donation (lbs):");
        lblDonation.setBounds(300, 100, 100, 25);
        frmMainWindow.add(lblDonation);

        txtDonation = new JTextField();
        txtDonation.setBounds(400, 100, 150, 25);
        frmMainWindow.add(txtDonation);

        // Add placeholder text
        txtDonation.setText("0 if you plan to volunteer for a food pantry");  // Placeholder text
        txtDonation.setForeground(java.awt.Color.GRAY);

        txtDonation.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (txtDonation.getText().equals("0 if you plan to volunteer for a food pantry")) {
                    txtDonation.setText("");
                    txtDonation.setForeground(java.awt.Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (txtDonation.getText().isEmpty()) {
                    txtDonation.setText("0 if you plan to volunteer for a food pantry");
                    txtDonation.setForeground(java.awt.Color.GRAY);
                }
            }
        });

        // Button to add volunteer
        btnAddVolunteer = new JButton("Add Volunteer");
        btnAddVolunteer.setBounds(400, 140, 150, 30);
        frmMainWindow.add(btnAddVolunteer);

        // JList for Organizations
        orgListModel = new DefaultListModel<>();
        orgList = new JList<>(orgListModel);
        JScrollPane scrollPane = new JScrollPane(orgList);
        scrollPane.setBounds(20, 300, 350, 50);
        frmMainWindow.add(scrollPane);

        btnSignUp = new JButton("Sign Up Volunteer");
        btnSignUp.setBounds(400, 300, 150, 30);
        frmMainWindow.add(btnSignUp);

        btnAddVolunteer.addActionListener(e -> handleAddVolunteer());
        btnSignUp.addActionListener(e -> handleVolunteerSignup());

        frmMainWindow.setVisible(true);
    }
    /**
     * Handles the addition of a new volunteer based on the input provided in the GUI.
     * Validates the input, creates a new Volunteer object, and adds it to the volunteer list.
     * Updates the list of matching organizations and displays a success or error message.
     */
    private void handleAddVolunteer() {
        try {
            String fullName = txtFullName.getText().trim();
            int age = Integer.parseInt(txtAge.getText().trim());
            String dayAvailable = (String) cmbDayAvailable.getSelectedItem();
            String[] startTimeParts = txtStartTime.getText().trim().split(":");
            String[] endTimeParts = txtEndTime.getText().trim().split(":");
            int startHour = Integer.parseInt(startTimeParts[0]);
            int startMinute = Integer.parseInt(startTimeParts[1]);
            int endHour = Integer.parseInt(endTimeParts[0]);
            int endMinute = Integer.parseInt(endTimeParts[1]);
            double distance = Double.parseDouble(txtDistance.getText().trim());
            boolean needsTransport = chkNeedsTransport.isSelected();
            double latitude = Double.parseDouble(txtLatitude.getText().trim());
            double longitude = Double.parseDouble(txtLongitude.getText().trim());
            double donation = Double.parseDouble(txtDonation.getText().trim());

            Location location = new Location(latitude, longitude, "", "", "", "");
            currentVolunteer = new Volunteer(String.valueOf(volunteersList.size()), fullName, age, location, dayAvailable,
                    new TimeFrame(startHour, startMinute, endHour, endMinute), distance, needsTransport, donation);
            volunteersList.add(currentVolunteer);
            updateMatchingOrganizations();
            JOptionPane.showMessageDialog(null, "Volunteer added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Invalid input!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    /**
     * Updates the list of community food organizations that match the current volunteer's preferences.
     * Filters the organizations based on the volunteer's criteria and sorts them for display.
     * Clears the existing organization list in the GUI and repopulates it with the matching results.
     */
    private void updateMatchingOrganizations() {
        orgListModel.clear();
        ArrayList<CommunityFoodOrg> matchingOrgs = new ArrayList<>();

        for (CommunityFoodOrg org : organizationsList) {
            if (currentVolunteer.orgMatch(org)) {
                matchingOrgs.add(org);
            }
        }
        selectionSortOrganizations(matchingOrgs);
        for (CommunityFoodOrg org : matchingOrgs) {
            orgListModel.addElement(org.getName());
        }
    }

    /**
     * Sorts a list of community food organizations in descending order based on relevance 
     * to the current volunteer's availability and requirements.
     * For FoodBank organizations, the sorting is based on the daily donations needed.
     * For FoodPantry organizations, the sorting is based on the daily volunteer spots left.
     *
     * @param orgs the list of community food organizations to be sorted
     */
    private void selectionSortOrganizations(ArrayList<CommunityFoodOrg> orgs) {
        for (int i = 0; i < orgs.size() - 1; i++) {
            int maxIndex = i;
            for (int j = i + 1; j < orgs.size(); j++) {
                if (orgs.get(j) instanceof FoodBank) {
                    if (((FoodBank) orgs.get(j)).getDailyDonationsNeeded(currentVolunteer.getDayAvailable()) >
                        ((FoodBank) orgs.get(maxIndex)).getDailyDonationsNeeded(currentVolunteer.getDayAvailable())) {
                        maxIndex = j;
                    }
                } else if (orgs.get(j) instanceof FoodPantry) {
                    if (((FoodPantry) orgs.get(j)).dailyVolunteerSpotsLeft(currentVolunteer.getDayAvailable()) >
                        ((FoodPantry) orgs.get(maxIndex)).dailyVolunteerSpotsLeft(currentVolunteer.getDayAvailable())) {
                        maxIndex = j;
                    }
                }
            }
            CommunityFoodOrg temp = orgs.get(maxIndex);
            orgs.set(maxIndex, orgs.get(i));
            orgs.set(i, temp);
        }
    }
    /**
     * Handles the volunteer signup process.
     * Checks if a volunteer and a selected organization are available, then registers the volunteer
     * with the selected organization. Displays a success message upon successful signup.
     */
    private void handleVolunteerSignup() {
        String selectedOrgName = orgList.getSelectedValue();
        if (selectedOrgName != null && currentVolunteer != null) {
            for (CommunityFoodOrg org : organizationsList) {
                if (org.getName().equals(selectedOrgName)) {
                    currentVolunteer.signUp(org);
                    JOptionPane.showMessageDialog(null, "Volunteer signed up successfully for " + org.getName(), "Success", JOptionPane.INFORMATION_MESSAGE);
                    return;
                }
            }
        }
    }

    public static void main(String[] args) {
        new CommunityFoodOrgVolunteeringGUI();
    }
}
