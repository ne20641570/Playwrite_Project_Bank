package api.endpoints;

public class PetEndpoints {
    public static final String ADD_PET = "/pet";
    public static final String UPDATE_PET = "/pet";
    public static final String FIND_BY_STATUS = "/pet/findByStatus";
    public static final String GET_PET_BY_ID = "/pet/{petId}";
    public static final String UPDATE_PET_WITH_FORM = "/pet/{petId}";
    public static final String DELETE_PET = "/pet/{petId}";
    public static final String UPLOAD_IMAGE = "/pet/{petId}/uploadImage";
    public static final String FIND_STATUS_01="available";
    public static final String FIND_STATUS_02="pending";
    public static final String FIND_STATUS_03="sold";
}
