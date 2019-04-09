package zjut.vote.dao;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import zjut.vote.person.Student;
import zjut.vote.person.Teacher;

public class StudentDao extends BaseDao {

	private static final String EXCEL_XLS = "xls";
	private static final String EXCEL_XLSX = "xlsx";
	private static final String SPORT = "体育军训部";

	// 获取学生信息
	public Student getStudentInfo(String no) {
		String sql = "select * from Student where no=?;";
		String message = null;
		Student stu = new Student();
		try {
			Connection conn;
			conn = getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, no);
			ResultSet rst = pstmt.executeQuery();
			if (rst.next()) {
				stu.setCollege(rst.getString("college").trim());
				stu.setFirstVoteInCollege(rst.getString("firstVoteInCollege").trim());
				stu.setFirstVoteInSport(rst.getString("firstVoteInSport").trim());
				stu.setName(rst.getString("name").trim());
				stu.setNo(rst.getString("no").trim());
				stu.setSecondVote(rst.getInt("secondVote"));
				Gson gson = new Gson();
				JsonParser parser = new JsonParser();
				// 通过JsonParser对象可以把json格式的字符串解析成一个JsonElement对象
				JsonElement el = parser.parse(rst.getString("secondVoteNo").trim());
				// 把JsonElement对象转换成JsonArray
				JsonArray jsonArray = null;
				if (el.isJsonArray()) {
					jsonArray = el.getAsJsonArray();
				}
				// 遍历JsonArray对象
				String No = null;
				ArrayList<String> noList = new ArrayList<String>();
				Iterator it = jsonArray.iterator();
				while (it.hasNext()) {
					JsonElement e = (JsonElement) it.next();
					// JsonElement转换为JavaBean对象
					No = gson.fromJson(e, String.class);
					noList.add(No);
				}
				stu.setSecondVoteNo(noList);
			}
			pstmt.close();
			conn.close();
			return stu;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}

	}

	// 登陆判断
	public Student Login(String no, String password) {
		String sql = "select * from Student  where no=?;";
		String message = null;
		Student stu = new Student();
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, no);
			ResultSet rst = pstmt.executeQuery();
			if (rst.next()) {
				if (password.equals(rst.getString("password").trim())) {
					stu.setCollege(rst.getString("college").trim());
					stu.setFirstVoteInCollege(rst.getString("firstVoteInCollege").trim());
					stu.setFirstVoteInSport(rst.getString("firstVoteInSport").trim());
					stu.setName(rst.getString("name").trim());
					stu.setNo(rst.getString("no").trim());
					stu.setSecondVote(rst.getInt("secondVote"));
					if (!rst.getString("secondVoteNo").trim().equals("")) {
						Gson gson = new Gson();
						JsonParser parser = new JsonParser();
						// 通过JsonParser对象可以把json格式的字符串解析成一个JsonElement对象
						JsonElement el = parser.parse(rst.getString("secondVoteNo").trim());
						System.out.println("No:" + rst.getString("secondVoteNo").trim());
						// 把JsonElement对象转换成JsonArray
						JsonArray jsonArray = null;
						if (el.isJsonArray()) {
							jsonArray = el.getAsJsonArray();
						}
						// 遍历JsonArray对象
						String No = null;
						ArrayList<String> noList = new ArrayList<String>();
						Iterator it = jsonArray.iterator();
						while (it.hasNext()) {
							JsonElement e = (JsonElement) it.next();
							// JsonElement转换为JavaBean对象
							No = gson.fromJson(e, String.class);
							noList.add(No);
						}
						stu.setSecondVoteNo(noList);
					} else {
						ArrayList<String> list = new ArrayList<String>();
						stu.setSecondVoteNo(list);
					}
					stu.setIsChange(rst.getInt("isChange"));
				} else {
					stu.setName("密码错误");
					stu.setNo("-1");
				}
			} else {
				stu.setName("用户名错误");
				stu.setNo("-1");
			}
			pstmt.close();
			conn.close();
			return stu;
		} catch (Exception e) {
			// TODO Auto-generated catch block

			e.printStackTrace();
			stu.setName("未知错误");
			stu.setNo("-1");
			return stu;
		}

	}

	// 一轮投票获取本学院老师信息
	public ArrayList<Teacher> getFirstVoteTeacherInCollege(String college) {
		String sql = "select * from Teacher1 where college=?";
		ArrayList<Teacher> list = new ArrayList<Teacher>();
		Connection conn;
		conn = getConnection();
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, college);
			ResultSet rst = pstmt.executeQuery();
			while (rst.next()) {
				Teacher te = new Teacher();
				te.setBirth(rst.getString("birth"));
				te.setCollege(rst.getString("college"));
				te.setCourse(rst.getString("course"));
				te.setIntroduction(rst.getString("introduction"));
				te.setName(rst.getString("name"));
				te.setNo(rst.getString("no"));
				te.setPicture(rst.getString("picture"));
				te.setSex(rst.getString("sex"));
				list.add(te);
			}
			pstmt.close();
			conn.close();
			return list;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			try {
				conn.close();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			return null;
		}
	}

	// 一轮留学生获取教师信息
	public ArrayList<Teacher> getFfirstVoteTeacher() {
		String sql = "select * from Teacher1 where isF=1;";
		ArrayList<Teacher> list = new ArrayList<Teacher>();
		Connection conn;
		conn = getConnection();
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			ResultSet rst = pstmt.executeQuery();
			while (rst.next()) {
				Teacher te = new Teacher();
				te.setBirth(rst.getString("birth"));
				te.setCollege(rst.getString("college"));
				te.setCourse(rst.getString("course"));
				te.setIntroduction(rst.getString("introduction"));
				te.setName(rst.getString("name"));
				te.setNo(rst.getString("no"));
				te.setPicture(rst.getString("picture"));
				te.setSex(rst.getString("sex"));
				list.add(te);
			}
			pstmt.close();
			conn.close();
			return list;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			try {
				conn.close();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			e.printStackTrace();
			return null;
		}
	}

	// 一轮投票获取体军部老师信息
	public ArrayList<Teacher> getFirstVoteTeacherInSport() {
		String sql = "select * from Teacher1 where college=?";
		ArrayList<Teacher> list = new ArrayList<Teacher>();
		Connection conn;
		conn = getConnection();
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, "体育军训部");
			ResultSet rst = pstmt.executeQuery();
			while (rst.next()) {
				Teacher te = new Teacher();
				te.setBirth(rst.getString("birth"));
				te.setCollege(rst.getString("college"));
				te.setCourse(rst.getString("course"));
				te.setIntroduction(rst.getString("introduction"));
				te.setName(rst.getString("name"));
				te.setNo(rst.getString("no"));
				te.setPicture(rst.getString("picture"));
				te.setSex(rst.getString("sex"));
				list.add(te);
			}
			pstmt.close();
			conn.close();
			return list;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			try {
				conn.close();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			return null;
		}
	}

	// 返回一轮投票某老师信息
	public Teacher getTeacher1Info(String no) {
		String sql = "select * from Teacher1 where no=?";
		Teacher te = new Teacher();
		Connection conn;
		conn = getConnection();
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, no);
			ResultSet rst = pstmt.executeQuery();
			if (rst.next()) {
				te.setBirth(rst.getString("birth"));
				te.setCollege(rst.getString("college"));
				te.setCourse(rst.getString("course"));
				te.setIntroduction(rst.getString("introduction"));
				te.setName(rst.getString("name"));
				te.setNo(rst.getString("no"));
				te.setPicture(rst.getString("picture"));
				te.setSex(rst.getString("sex"));
			}
			pstmt.close();
			conn.close();
			return te;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			try {
				conn.close();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			return null;
		}
	}

	// 返回二轮投票某老师信息(中文)
	public Teacher getTeacher2Info(String no) {
		String sql = "select * from Teacher2 where no=?";
		Teacher te = new Teacher();
		Connection conn;
		conn = getConnection();
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, no);
			ResultSet rst = pstmt.executeQuery();
			if (rst.next()) {
				te.setBirth(rst.getString("birth"));
				te.setCollege(rst.getString("college"));
				te.setCourse(rst.getString("course"));
				te.setIntroduction(rst.getString("introduction"));
				te.setName(rst.getString("name"));
				te.setNo(rst.getString("no"));
				te.setPicture(rst.getString("picture"));
				te.setSex(rst.getString("sex"));
			}
			pstmt.close();
			conn.close();
			return te;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			try {
				conn.close();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			e.printStackTrace();
			return null;
		}
	}

	// 一轮留学生投票
	public Student FfirstVote(Student stu, String no) {
		String sql = "update Student set firstVoteInCollege =? where no=?;";
		int count = 0;
		Connection conn;
		conn = getConnection();
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, no);
			pstmt.setString(2, stu.getNo());
			count = pstmt.executeUpdate();
			if (count > 0) {
				stu.setFirstVoteInCollege(no);
				sql = "update Teacher1 set firstVote=firstVote+1 where no=?;";
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, no);
				pstmt.executeUpdate();
			}
			pstmt.close();
			conn.close();
			return stu;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			try {
				conn.close();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			return null;
		}
	}

	// 一轮投票
	public Student FirstVote(Student stu, String no, String college) {
		String sql = "update Student set ";
		int count = 0;
		if (college.equals(SPORT)) {
			sql += "firstVoteInSport=? where no=?;";
		} else {
			sql += "firstVoteInCollege=? where no=?;";
		}
		Connection conn;
		conn = getConnection();
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, no);
			pstmt.setString(2, stu.getNo());
			count = pstmt.executeUpdate();
			if (count > 0) {
				if (college.equals(SPORT)) {
					stu.setFirstVoteInSport(no);
				} else {
					stu.setFirstVoteInCollege(no);
				}
				sql = "update VoteInZjut.Teacher1 set firstVote=firstVote+1 where no=?;";
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, no);
				pstmt.executeUpdate();
			}
			pstmt.close();
			conn.close();
			return stu;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			try {
				conn.close();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			e.printStackTrace();
			return null;
		}
	}

	// 返回二轮投票老师列表(中文)
	public ArrayList<Teacher> getSecondVoteTeacher(Student stu) {
		String sql = "select * from Teacher2;";
		ArrayList<String> doneNo = stu.getSecondVoteNo();
		ArrayList<Teacher> list = new ArrayList<Teacher>();
		Connection conn;
		conn = getConnection();
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			ResultSet rst = pstmt.executeQuery();
			while (rst.next()) {
				Teacher te = new Teacher();
				te.setNo(rst.getString("no"));
				te.setPicture(rst.getString("picture"));
				boolean flag = true;
				for (int i = 0; i < doneNo.size(); i++) {
					if (te.getNo().equals(doneNo.get(i))) {
						flag = false;
					}
				}
				if (flag)
					list.add(te);
			}
			pstmt.close();
			conn.close();
			return list;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			try {
				conn.close();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			return null;
		}
	}

	// 二轮投票(中文)
	public Student secondVote(Student stu, String no) {
		String sql = "update Student set secondVoteNo=? where no=?;";
		int count = 0;
		Connection conn;
		conn = getConnection();
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			ArrayList<String> list = stu.getSecondVoteNo();
			list.add(no);
			Gson gson = new Gson();
			System.out.println("list:" + gson.toJson(list));
			pstmt.setString(1, gson.toJson(list));
			pstmt.setString(2, stu.getNo());
			count += pstmt.executeUpdate();
			sql = "update Teacher2 set secondVote=secondVote+1 where no=?;";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, no);
			count += pstmt.executeUpdate();
			sql = "update Student set secondVote=secondVote+1 where no=?;";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, stu.getNo());
			count += pstmt.executeUpdate();
			pstmt.close();
			conn.close();
			if (count > 0) {
				stu = getStudentInfo(stu.getNo());
			}
			return stu;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			try {
				conn.close();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			return null;
		}

	}

	// 根据学院返回二轮投票老师
	public ArrayList<Teacher> getTeacherInCollege(String college) {
		String sql = "select * from Teacher2 ";
		if (college.equals("所有学院")) {
		} else {
			sql += "where college=?;";
		}
		ArrayList<Teacher> list = new ArrayList<Teacher>();
		Connection conn;
		conn = getConnection();
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			if (!college.equals("所有学院")) {
				pstmt.setString(1, college);
			}
			ResultSet rst = pstmt.executeQuery();
			while (rst.next()) {
				Teacher te = new Teacher();
				te.setCollege(rst.getString("college"));
				te.setName(rst.getString("name"));
				te.setNo(rst.getString("no"));
				te.setPicture(rst.getString("picture"));
				te.setSex(rst.getString("sex"));
				list.add(te);
			}
			pstmt.close();
			conn.close();
			return list;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			try {
				conn.close();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			return null;
		}
	}

	// 记录日志到文件
	/*
	 * public void log(String content) { String
	 * filePath="C://Users/于一/Desktop/最美老师/log.txt"; try { FileWriter fw = new
	 * FileWriter(filePath, true); BufferedWriter bw = new BufferedWriter(fw);
	 * bw.write(content);// 往已有的文件上添加字符串 bw.close(); fw.close(); } catch (Exception
	 * e) { // TODO Auto-generated catch block e.printStackTrace(); } OutputStream
	 * out = null; try { // 读取Excel文档 File finalXlsxFile = new File(filePath);
	 * Workbook workBook = getWorkbok(finalXlsxFile); // sheet 对应一个工作页 Sheet sheet =
	 * workBook.getSheetAt(0);
	 * 
	 * int rowNumber = sheet.getPhysicalNumberOfRows(); // 获取文件实际行数 //
	 * 创建文件输出流，输出电子表格：这个必须有，否则你在sheet上做的任何操作都不会有效 out = new
	 * FileOutputStream(filePath); workBook.write(out); /** 往Excel中写新数据
	 */
	/*
	 * Row row=sheet.createRow(rowNumber); Cell one=row.createCell(0);
	 * one.setCellValue(no); Cell two=row.createCell(1); two.setCellValue(name);
	 * Cell three=row.createCell(2); three.setCellValue(no1); Cell
	 * four=row.createCell(3); four.setCellValue(college); //
	 * 创建文件输出流，准备输出电子表格：这个必须有，否则你在sheet上做的任何操作都不会有效 out = new
	 * FileOutputStream(filePath); workBook.write(out); } catch (Exception e) {
	 * e.printStackTrace(); } finally{ try { if(out != null){ out.flush();
	 * out.close(); } } catch (IOException e) { e.printStackTrace(); } }
	 * System.out.println("数据导出成功");
	 */
	// }

	/**
	 * 判断Excel的版本,获取Workbook
	 * 
	 * @param in
	 * @param filename
	 * @return
	 * @throws IOException
	 */
	/*
	 * public static Workbook getWorkbok(File file) throws IOException{ Workbook wb
	 * = null; FileInputStream in = new FileInputStream(file);
	 * if(file.getName().endsWith(EXCEL_XLS)){ //Excel&nbsp;2003 wb = new
	 * HSSFWorkbook(in); }else if(file.getName().endsWith(EXCEL_XLSX)){ // Excel
	 * 2007/2010 wb = new XSSFWorkbook(in); } return wb; }
	 */
	// 二轮投票修改密码
	public boolean changePsw(String password, String no) {
		String sql = "update Student set password=? where no=?;";
		Connection conn;
		conn = getConnection();
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, password);
			pstmt.setString(2, no);
			int count = 0;
			count += pstmt.executeUpdate();
			sql = "update Student set isChange=1 where no=?;";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, no);
			count += pstmt.executeUpdate();
			pstmt.close();
			conn.close();
			return true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			try {
				conn.close();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			return false;
		}
	}

}
