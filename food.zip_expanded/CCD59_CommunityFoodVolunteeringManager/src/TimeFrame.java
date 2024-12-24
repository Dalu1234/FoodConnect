	    /**
	    * Class TimeFrame
	    * author: Chukwudalu Dumebi-Kachikwu
	    * created: 10/17/2024
	    */

public class TimeFrame {
    private int hourStart, minuteStart, hourEnd, minuteEnd;

    // Constructor with full time (hour and minute)
    public TimeFrame(int hs, int ms, int he, int me) {
        this.hourStart = (hs >= 0 && hs <= 23) ? hs : 8; // default 8 if out of bounds
        this.minuteStart = (ms >= 0 && ms <= 59) ? ms : 0; // default 0
        this.hourEnd = (he >= 0 && he <= 23) ? he : 8; // default 8 if out of bounds
        this.minuteEnd = (me >= 0 && me <= 59) ? me : 0; // default 0
    }

    // Constructor with only hours (minutes default to 0)
    public TimeFrame(int hs, int he) {
        this(hs, 0, he, 0);
    }

    //check if TimeFrame is fully contained
    public boolean timeFrameMatch(TimeFrame orgTime) {
        // Check if the volunteer's start time is after or equal to the organization's start time
        if (this.hourStart > orgTime.hourStart || 
            (this.hourStart == orgTime.hourStart && this.minuteStart >= orgTime.minuteStart)) {

            // Check if the volunteer's end time is before or equal to the organization's end time
            if (this.hourEnd < orgTime.hourEnd || 
                (this.hourEnd == orgTime.hourEnd && this.minuteEnd <= orgTime.minuteEnd)) {

                return true; // The volunteer's time is fully contained within the organization's time frame
            }
        }
        return false; // The volunteer's time is not fully contained by organization availability
    }


}

