package com.example.backendapp.Repositories;

import com.example.backendapp.Config.ClientDataSource;
import net.minidev.json.JSONArray;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.SQLException;

import static java.sql.Types.REF_CURSOR;

@Repository
public class JsonRepository {

    public JSONArray ExportTracksForUser(Long userId) throws SQLException {
        Connection clientConnection = ClientDataSource.getConnection();
        java.sql.CallableStatement stmt = clientConnection.prepareCall("{call KURSACH_ADMIN.TRACKS_TO_JSON(?,?)}");
        stmt.setLong(1, userId);
        stmt.registerOutParameter(2, REF_CURSOR);
        stmt.execute();
        java.sql.ResultSet rs = (java.sql.ResultSet) stmt.getObject(2);

        var json = new JSONArray();

        while (rs.next()) {
            var playlist = rs.getObject(1);
            System.out.println(playlist);
            json = json.appendElement(playlist);
        }


        clientConnection.close();
        stmt.close();
        rs.close();
        return json;
    }
}
