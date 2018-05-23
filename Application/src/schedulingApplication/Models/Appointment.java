package schedulingApplication.Models;

import java.time.ZoneId;

public class Appointment {
    private int id;
    private int customerId;
    private String customerName;
    private int userId;
    private String title;
    private String description;
    private String location;
    private String contact;
    private String type;
    private String url;
    private String startTime;
    private String endTime;
    private String startingHour;
    private String startingMinute;
    private String endingHour;
    private String endingMinute;
    private String day;
    
    private int pheonixArizonaTimeOffset = 0;
    private int newYorkNewYorkTimeOffset = 3;
    private int londonEnglandTimeOffset = 8;

    private ZoneId currentLocale = ZoneId.systemDefault();

    public Appointment(int id, String customerName, int customerId, int userId, String title, String location, String contact, String type, String url, String startTime, String endTime, String day, String startingHour, String startingMinute, String endingHour, String endingMinute) {
        this.id = id;
        this.customerName = customerName;
        this.customerId = customerId;
        this.userId = userId;
        this.title = title;
        this.location = location;
        this.contact = contact;
        this.type = type;
        this.url = url;
        this.startTime = startTime;
        this.endTime = endTime;
        this.day = day;
        this.description = type;
        this.startingHour = startingHour;
        this.startingMinute = startingMinute;
        this.endingHour = endingHour;
        this.endingMinute = endingMinute;
        
    }

    public String getStartingHour() {
        return adjustTime(startingHour);
    }

    public String getStartingMinute() {
        return startingMinute;
    }

    public String getEndingHour() {
        return adjustTime(endingHour);
    }

    public String getEndingMinute() {
        return endingMinute;
    }
    
    public int getId() {
        return id;
    }

    public int getCustomerId() {
        return customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public int getUserId() {
        return userId;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getLocation() {
        return location;
    }

    public String getContact() {
        return contact;
    }

    public String getType() {
        return type;
    }

    public String getUrl() {
        return url;
    }

    public String getStartTime() {
        return getStartingHour().concat(":"+getStartingMinute());
    }

    public String getEndTime() {
        return getEndingHour().concat(":"+getEndingMinute());
    }

    public String getDay() {
        boolean foundSpace = false;
        String editedDay = "";
        for(int i =0; i < day.length(); i++){
            if(!foundSpace){
                if(day.charAt(i) == ' '){
                    foundSpace = true;
                }else{
                    editedDay = editedDay.concat(String.valueOf(day.charAt(i)));
                }
            }
        }
        return editedDay;
    }
    
    private String adjustTime (String time){
        int integerTime = Integer.parseInt(time);
        
        if(currentLocale.equals(ZoneId.of("America/New_York"))){
            integerTime += newYorkNewYorkTimeOffset;
        }else if(currentLocale.equals(ZoneId.of("Europe/London"))){
            integerTime += londonEnglandTimeOffset;
        }
        return String.valueOf(integerTime);
    }
}
