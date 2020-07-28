package domain;

public class Favor {
    private Long favorID;
    private Long uID;
    private Long imageID;

    public Favor() {
    }

    public Favor(Long uID, Long imageID) {
        this.uID = uID;
        this.imageID = imageID;
    }

    public Long getFavorID() {
        return favorID;
    }

    public void setFavorID(Long favorID) {
        this.favorID = favorID;
    }

    public Long getUID() {
        return uID;
    }

    public void setUID(Long uID) {
        this.uID = uID;
    }

    public Long getImageID() {
        return imageID;
    }

    public void setImageID(Long imageID) {
        this.imageID = imageID;
    }
}
