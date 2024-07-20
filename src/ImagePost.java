public class ImagePost extends TextPost{
    private String imageFileName;
    private int imageWidth;
    private int imageHeight;
    /**
     * @return the imageFileName
     */
    public String getImageFileName() { // this will get ImageFileName and return it in string type
        return imageFileName;
    }

    public void setImageFileName(String imageFileName) { // this will set ImageFileName
        this.imageFileName = imageFileName;
    }

    public int getImageWidth() {// this will get Image width and return it in int type
        return imageWidth;
    }


    public void setImageWidth(int imageWidth) { // this will set Image width
        this.imageWidth = imageWidth;
    }

    public int getImageHeight() { // this will get Image Height and return it in int type
        return imageHeight;
    }

    public void setImageHeight(int imageHeight) { // this will set Image Height
        this.imageHeight = imageHeight;
    }


    @Override

    public String toString(){
        return super.toString()+ "\nImage: " + this.imageFileName + "\nImage resolution: "
                + this.imageWidth + "x"
                + this.imageHeight;
    }


}