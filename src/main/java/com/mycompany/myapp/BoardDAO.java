package com.mycompany.myapp;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.mycompany.myapp.BoardVO;
import com.mycompany.myapp.JDBCUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class BoardDAO {
	
	Connection conn = null;
	PreparedStatement stmt = null;
	ResultSet rs = null;

	private final String BOARD_INSERT = "insert into BOARD (category, title, writer, content) values (?,?,?,?)";
	private final String BOARD_UPDATE = "update BOARD set category=?, title=?, writer=?, content=? where seq=?";
	private final String BOARD_DELETE = "delete from BOARD  where seq=?";
	private final String BOARD_GET = "select * from BOARD  where seq=?";
	private final String BOARD_LIST = "select * from BOARD order by seq desc";
	@Autowired
	private JdbcTemplate template;

	public int insertBoard(BoardVO vo) {
		System.out.println("===> JDBC로 insertBoard() 기능 처리");

		return template.update(BOARD_INSERT, new Object[]{vo.getCategory(), vo.getTitle(), vo.getWriter(), vo.getContent()});
	}

	// 글 삭제
	public int deleteBoard(int id) {
		System.out.println("===> JDBC로 deleteBoard() 기능 처리");

		return template.update(BOARD_DELETE, new Object[]{id});
	}
	public int updateBoard(BoardVO vo) {
		System.out.println("===> JDBC로 updateBoard() 기능 처리");

		return template.update(BOARD_UPDATE, new Object[]{vo.getCategory(), vo.getTitle(), vo.getWriter(), vo.getContent(), vo.getSeq()});
	}	
	
	public BoardVO getBoard(int seq) {
		System.out.println("===> JDBC로 getBoard() 기능 처리");
		return template.queryForObject(BOARD_GET, new Object[]{seq}, new BeanPropertyRowMapper<BoardVO>(BoardVO.class));
	}
	
	public List<BoardVO> getBoardList(){
		System.out.println("===> JDBC로 getBoardList() 기능 처리");

		return template.query(BOARD_LIST, new RowMapper<BoardVO>() {

			@Override
			public BoardVO mapRow(ResultSet rs, int rowNum) throws SQLException {
				BoardVO data = new BoardVO();
				data.setSeq(rs.getInt("seq"));
				data.setCategory(rs.getString("category"));
				data.setContent(rs.getString("content"));
				data.setTitle(rs.getString("title"));
				data.setRegdate(rs.getDate("regdate"));
				data.setWriter(rs.getString("writer"));
				return data;
			}
		});
	}

	public void setTemplate(JdbcTemplate template) {
		this.template = template;
	}
}
