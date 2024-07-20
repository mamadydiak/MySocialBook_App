public class VideoPost extends TextPost{
    private String videoFilename;
    private int maximumVideoLength;

    // here is for getting and setting Video File name
    public String getVideoFilename() {
        return videoFilename;
    }

    public void setVideoFilename(String videoFilename) {
        this.videoFilename = videoFilename;
    }

    public int getMaximumVideoLength() {
        return maximumVideoLength;
    }

    public void setMaximumVideoLength(int maximumVideoLength) {
        this.maximumVideoLength = maximumVideoLength;
    }


    public String toString(){
        return super.toString()+ "\nVideo: " + this.videoFilename + "\nVideo duration: "
                + maximumVideoLength +" minutes";
    }

}