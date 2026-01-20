package api.models.pet;

import java.util.List;

public class Pet {
    public int id;
    public Category category;
    public String name;
    public List<String> photoUrls;
    public List<Tag> tags;
    public String status;
}
