import java.util.Date;
import java.util.UUID;

public interface PostInterface {
    void setText(String text);
    public String getText();
    public UUID getID();
    public Date getDate();
}