package DAO;

import domain.Favor;

import java.util.List;

public class FavorDAO extends DAO<Favor> {
    public List<Favor> getListByUID(Long uID) {
        String sql = "SELECT * FROM travelimagefavor WHERE UID = ? ORDER BY ImageID";
        return getForList(sql, uID);
    }

    public Favor getByUIDAndImageID(Long uID, Long imageID) {
        String sql = "SELECT * FROM travelimagefavor WHERE UID = ? AND ImageID = ?";
        return get(sql, uID, imageID);
    }

    public void deleteByImageID(Long imageID) {
        String sql = "DELETE FROM travelimagefavor WHERE ImageID = ?";
        update(sql, imageID);
    }

    public void deleteByUIDAndImageID(Long uID, Long imageID) {
        String sql = "DELETE FROM travelimagefavor WHERE UID = ? AND ImageID = ?";
        update(sql, uID, imageID);
    }

    public void save(Favor favor) {
        String sql = "INSERT INTO travelimagefavor (UID, ImageID) VALUES (?, ?)";
        update(sql, favor.getUID(), favor.getImageID());
    }
}
