package org.newdawn.spaceinvaders.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

import org.newdawn.spaceinvaders.model.RecordInfo;

public class Record {

	private Connection con;
	private PreparedStatement pstm;
	private ResultSet rs;
	private RecordInfo info;
	private LinkedList<RecordInfo> list;

	public void insert(String name, double record) throws ClassNotFoundException, SQLException {

		con = DbConnection.getMySQLConnection();

		String sql = "insert into record values(?,?)";
		pstm = con.prepareStatement(sql);

		pstm.setString(1, name);
		pstm.setDouble(2, record);

		pstm.executeUpdate();
	}

	public LinkedList getRecord() throws ClassNotFoundException, SQLException {

		con = DbConnection.getMySQLConnection();
		list = new LinkedList<RecordInfo>();

		String sql = "select * from record order by record asc";

		pstm = con.prepareStatement(sql);
		rs = pstm.executeQuery();

		while (rs.next()) {
			info = new RecordInfo();

			info.setName(rs.getString("name"));
			info.setRecord(rs.getDouble("record"));

			list.add(info);

		}
		return list;
	}

}