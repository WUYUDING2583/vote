package zjut.vote.dao;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFPictureData;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.google.gson.Gson;
import com.mysql.jdbc.StringUtils;

import zjut.vote.person.Student;
import zjut.vote.person.Teacher;

public class AdminDao extends BaseDao {

	// 文件保存路径
	private static final String UPLOAD_DIRECTORY = "upload";
	private static final String PICTURE_DIRECTORY = "picture";

	// 文件上传参数
	private static final int MEMORY_THRESHOLD = 1024 * 1024 * 3; // 3MB
	private static final int MAX_FILE_SIZE = 1024 * 1024 * 40; // 40MB
	private static final int MAX_REQUEST_SIZE = 1024 * 1024 * 50; // 50MB

	private static final String ALLCOLLEGE = "所有学院";

	private XSSFCell Ttel;

	// 获取10为随机数
	public String getRandomString() {
		// 从以下字符中选择
		String str = "zxcvbnmlkjhgfdsaqwertyuiopQWERTYUIOPASDFGHJKLZXCVBNM1234567890";
		Random random = new Random();
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < 10; ++i) {
			int number = random.nextInt(62);
			sb.append(str.charAt(number));
		}
		return sb.toString();
	}

	// 文件上传
	public ArrayList<File> fileUpload(HttpServletRequest request, HttpServletResponse response, String path)
			throws FileUploadException {

		if (!ServletFileUpload.isMultipartContent(request)) {

			return null;
		}

		ArrayList<File> list = new ArrayList<File>();
		DiskFileItemFactory factory = new DiskFileItemFactory();
		factory.setSizeThreshold(MEMORY_THRESHOLD);
		factory.setRepository(new File(System.getProperty("java.io.tmpdir")));

		ServletFileUpload upload = new ServletFileUpload(factory);

		upload.setFileSizeMax(MAX_FILE_SIZE);

		upload.setSizeMax(MAX_REQUEST_SIZE);

		upload.setHeaderEncoding("UTF-8");

		String uploadPath = path + UPLOAD_DIRECTORY;
		File storeFile = null;

		File uploadDir = new File(uploadPath);
		if (!uploadDir.exists()) {
			uploadDir.mkdir();
		}

		try {
			@SuppressWarnings("unchecked")
			List<FileItem> formItems = upload.parseRequest(request);

			if (formItems != null && formItems.size() > 0) {
				for (FileItem item : formItems) {
					if (!item.isFormField()) {
						String fileName = new File(item.getName()).getName();
						String filePath = uploadPath + File.separator + fileName;
						storeFile = new File(filePath);
						System.out.println(filePath);
						item.write(storeFile);
						list.add(storeFile);
						request.getSession().setAttribute("uploadMessage", "文件上传成功！");
					}
				}
			}

		} catch (Exception ex) {
			return null;
		}

		return list;
	}

	// 一轮教师信息上传
	public int UploadTeacher(ArrayList<File> list, String path) {
		XSSFCell name, sex, birth, college, course, introduction, picture, isF;
		XSSFRow row;
		int count = 0;
		XSSFWorkbook wb0 = null;
		String message = null;
		Teacher te = new Teacher();
		try {
			FileInputStream fs;
			for (int j = 0; j < list.size(); j++) {
				fs = new FileInputStream(list.get(j));
				wb0 = new XSSFWorkbook(fs);
				XSSFSheet sht0 = wb0.getSheetAt(0);
				row = sht0.getRow(0);
				name = row.getCell(1);
				sex = row.getCell(3);
				picture = row.getCell(4);

				row = sht0.getRow(1);
				birth = row.getCell(1);
				college = row.getCell(3);

				row = sht0.getRow(2);
				course = row.getCell(1);
				isF = row.getCell(3);

				row = sht0.getRow(6);
				introduction = row.getCell(1);

				birth.setCellType(CellType.STRING);
				te.setBirth(birth.getStringCellValue());
				te.setCollege(college.getStringCellValue());
				te.setIntroduction(introduction.getStringCellValue());
				te.setName(name.getStringCellValue());
				te.setCourse(course.getStringCellValue());
				if (isF.getStringCellValue().equals("是"))
					te.setIsF(1);
				else
					te.setIsF(0);
				System.out.println(te.getCourse());
				te.setNo(getRandomString());
				te.setSex(sex.getStringCellValue());
				// 获取图片
				List<XSSFPictureData> pictures = wb0.getAllPictures();
				for (int i = 0; i < pictures.size(); i++) {
					XSSFPictureData pictureData = pictures.get(i);
					byte[] picData = pictureData.getData();
					System.out.println("image-size:" + picData.length);
					int stateInt = 1;
					if (picData.length > 0) {
						try {
							String filePath = path + PICTURE_DIRECTORY;
							File validateCodeFolder = new File(filePath);
							if (!validateCodeFolder.exists()) {
								validateCodeFolder.mkdirs();
							}
							String type = ".jpg";
							String uuid = te.getName();
							String filename = uuid + type;
							InputStream in = new ByteArrayInputStream(picData);
							File file1 = new File(filePath, filename);
							System.out.println(filePath + "\n" + filename);
							te.setPicture("picture" + File.separator + filename);
							FileOutputStream fos = new FileOutputStream(file1);
							byte[] b = new byte[1024];
							int nRead = 0;
							while ((nRead = in.read(b)) != -1) {
								fos.write(b, 0, nRead);
							}
							fos.flush();
							fos.close();
							in.close();
							System.out.println("图片获取成功");

						} catch (Exception e) {
							stateInt = 0;
							e.printStackTrace();
						} finally {

						}
					} else {
						System.out.println("图片获取失败");
					}
				}

				String sql = "insert into Teacher1 values(?,?,?,?,?,?,?,?,0,?);";
				Connection conn = getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, te.getNo());
				pstmt.setString(2, te.getName());
				pstmt.setString(3, te.getSex());
				pstmt.setString(4, te.getBirth());
				pstmt.setString(5, te.getCollege());
				pstmt.setString(6, te.getCourse());
				pstmt.setString(7, te.getPicture());
				pstmt.setString(8, te.getIntroduction());
				pstmt.setInt(9, te.getIsF());
				pstmt.executeUpdate();
				count++;
				fs.close();
				list.get(j).delete();
				pstmt.close();
				conn.close();
			}
			return count;
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return count;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return count;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return count;
		}

	}

	// 上传二轮教师信息
	public int UploadTeacher2(ArrayList<File> list, String path) {
		XSSFCell name, sex, birth, college, course, introduction, picture,no;
		XSSFRow row;
		int count = 0;
		XSSFWorkbook wb0 = null;
		String message = null;
		Teacher te = new Teacher();
		try {
			FileInputStream fs;
			for (int j = 0; j < list.size(); j++) {
				fs = new FileInputStream(list.get(j));
				wb0 = new XSSFWorkbook(fs);
				XSSFSheet sht0 = wb0.getSheetAt(0);
				row = sht0.getRow(0);
				name = row.getCell(1);
				sex = row.getCell(3);
				picture = row.getCell(4);

				row = sht0.getRow(1);
				birth = row.getCell(1);
				college = row.getCell(3);

				row = sht0.getRow(2);
				course = row.getCell(1);
				no=row.getCell(2);

				row = sht0.getRow(6);
				introduction = row.getCell(1);

				birth.setCellType(CellType.STRING);
				te.setBirth(birth.getStringCellValue());
				te.setCollege(college.getStringCellValue());
				te.setIntroduction(introduction.getStringCellValue());
				te.setName(name.getStringCellValue());
				te.setCourse(course.getStringCellValue());
				System.out.println(te.getCourse());
				te.setSex(sex.getStringCellValue());
				no.setCellType(CellType.STRING);
				te.setNo(no.getStringCellValue());
				// 获取图片
				List<XSSFPictureData> pictures = wb0.getAllPictures();
				for (int i = 0; i < pictures.size(); i++) {
					XSSFPictureData pictureData = pictures.get(i);
					byte[] picData = pictureData.getData();
					System.out.println("image-size:" + picData.length);
					int stateInt = 1;
					if (picData.length > 0) {
						try {
							String filePath = path + PICTURE_DIRECTORY;
							File validateCodeFolder = new File(filePath);
							if (!validateCodeFolder.exists()) {
								validateCodeFolder.mkdirs();
							}
							String type = ".jpg";
							String uuid = te.getName();
							String filename = uuid + type;
							InputStream in = new ByteArrayInputStream(picData);
							File file1 = new File(filePath, filename);
							System.out.println(filePath + "\n" + filename);
							te.setPicture("picture" + File.separator + filename);
							FileOutputStream fos = new FileOutputStream(file1);
							byte[] b = new byte[1024];
							int nRead = 0;
							while ((nRead = in.read(b)) != -1) {
								fos.write(b, 0, nRead);
							}
							fos.flush();
							fos.close();
							in.close();
							System.out.println("图片获取成功");

						} catch (Exception e) {
							stateInt = 0;
							e.printStackTrace();
						} finally {

						}
					} else {
						System.out.println("图片获取失败");
					}
				}

				String sql = "insert into Teacher2 values(?,?,?,?,?,?,?,?,0);";
				Connection conn = getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, te.getNo());
				pstmt.setString(2, te.getName());
				pstmt.setString(3, te.getSex());
				pstmt.setString(4, te.getBirth());
				pstmt.setString(5, te.getCollege());
				pstmt.setString(6, te.getCourse());
				pstmt.setString(7, te.getPicture());
				pstmt.setString(8, te.getIntroduction());
				pstmt.executeUpdate();
				count++;
				fs.close();
				list.get(j).delete();
				pstmt.close();
				conn.close();
			}
			return count;
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return count;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return count;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return count;
		}
	}

	// 学生信息上传
	public int UploadStudent(ArrayList<File> fileList) {
		XSSFCell no, name, college;
		XSSFRow row;
		XSSFWorkbook wb0 = null;
		String message = null;
		int coun = 0;
		ArrayList<Student> list = new ArrayList<Student>();
		try {
			FileInputStream fs;
			for (int j = 0; j < fileList.size(); j++) {
				fs = new FileInputStream(fileList.get(j));
				wb0 = new XSSFWorkbook(fs);
				XSSFSheet sht0 = wb0.getSheetAt(0);
				for (int i = 1; i < sht0.getPhysicalNumberOfRows(); i++) {
					row = sht0.getRow(i);
					// 跳过空行
					if (i >= 1) {
						if (row == null) {
							System.out.println((i + 1) + "行为空");
							continue;
						} else if (row.getCell(0) == null
								|| StringUtils.isNullOrEmpty(row.getCell(0).getStringCellValue())) {
							continue;
						}
					}

					college = row.getCell(0);
					name = row.getCell(2);
					no = row.getCell(3);
					Student stu = new Student();
					stu.setCollege(college.getStringCellValue());
					stu.setName(name.getStringCellValue());
					no.setCellType(CellType.STRING);
					String temp = no.getStringCellValue();
					stu.setNo(temp);
					stu.setPassword(temp.substring(temp.length() - 6));
					list.add(stu);
				}
				String sql = "insert into Student values(?,?,?,?,'','',0,'',0);";
				Connection conn = getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql);
				for (int i = 0; i < list.size(); i++) {
					pstmt.setString(1, list.get(i).getNo());
					pstmt.setString(2, list.get(i).getName());
					pstmt.setString(3, list.get(i).getCollege());
					pstmt.setString(4, list.get(i).getPassword());
					coun += pstmt.executeUpdate();
				}
				fs.close();
				fileList.get(j).delete();
				pstmt.close();
				conn.close();
			}
			return coun;
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}
	}

	// 获得一轮投票结果
	public ArrayList<Teacher> getFirstVoteResult(String college) {
		String sql = "select name,college,firstVote from Teacher1 ";
		ArrayList<Teacher> list = new ArrayList<Teacher>();
		if (college.equals("所有学院")) {
			sql += "order by firstVote DESC;";
		} else {
			sql += "where college=? order by firstVote DESC;";
		}
		try {
			Connection conn;
			conn = getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql);
			if (!college.equals("所有学院")) {
				pstmt.setString(1, college);
			}
			ResultSet rst = pstmt.executeQuery();
			while (rst.next()) {
				Teacher te = new Teacher();
				te.setName(rst.getString("name"));
				te.setCollege(rst.getString("college"));
				te.setFirstVote(rst.getInt("firstVote"));
				list.add(te);
			}
			pstmt.close();
			conn.close();
			return list;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	// 导出一轮投票结果
	public HSSFWorkbook ExportFirstVote(String college) {
		ArrayList<Teacher> list = new ArrayList<Teacher>();
		list = getFirstVoteResult(college);
		// 鏂板缓excel鎶ヨ〃
		HSSFWorkbook excel = new HSSFWorkbook();
		// 娣诲姞涓�涓猻heet
		HSSFSheet hssfSheet = excel.createSheet(college + "一轮投票");
		// 寰�excel琛ㄦ牸鍒涘缓涓�琛岋紝excel鐨勮鍙锋槸浠�0寮�濮嬬殑
		// 璁剧疆琛ㄥご
		HSSFRow firstRow = hssfSheet.createRow(0);
		// 鍒涘缓鍗曞厓鏍�
		HSSFCell hssfCell = firstRow.createCell(0);
		hssfCell.setCellValue("姓名");
		hssfCell = firstRow.createCell(1);
		hssfCell.setCellValue("学院");
		hssfCell = firstRow.createCell(2);
		hssfCell.setCellValue("票数");
		for (int row = 0; row < list.size(); row++) {
			HSSFRow hssfRow = hssfSheet.createRow(row + 1);
			HSSFCell cell = hssfRow.createCell(0);
			cell.setCellValue(list.get(row).getName());
			cell = hssfRow.createCell(1);
			cell.setCellValue(list.get(row).getCollege());
			cell = hssfRow.createCell(2);
			cell.setCellValue(list.get(row).getFirstVote());
		}
		return excel;
	}

	// 导出一轮未投票名单
	public HSSFWorkbook ExportNotVoteInFirst(String college) {
		ArrayList<Student> list = new ArrayList<Student>();
		list = notVoteInFirst(college);
		// 鏂板缓excel鎶ヨ〃
		HSSFWorkbook excel = new HSSFWorkbook();
		// 娣诲姞涓�涓猻heet
		HSSFSheet hssfSheet = excel.createSheet(college + "一轮未投票");
		// 寰�excel琛ㄦ牸鍒涘缓涓�琛岋紝excel鐨勮鍙锋槸浠�0寮�濮嬬殑
		// 璁剧疆琛ㄥご
		HSSFRow firstRow = hssfSheet.createRow(0);
		// 鍒涘缓鍗曞厓鏍�
		HSSFCell hssfCell = firstRow.createCell(0);
		hssfCell.setCellValue("姓名");
		hssfCell = firstRow.createCell(1);
		hssfCell.setCellValue("学院");
		hssfCell = firstRow.createCell(2);
		hssfCell.setCellValue("学号");
		for (int row = 0; row < list.size(); row++) {
			HSSFRow hssfRow = hssfSheet.createRow(row + 1);
			HSSFCell cell = hssfRow.createCell(0);
			cell.setCellValue(list.get(row).getName());
			cell = hssfRow.createCell(1);
			cell.setCellValue(list.get(row).getCollege());
			cell = hssfRow.createCell(2);
			cell.setCellValue(list.get(row).getNo());
		}
		return excel;
	}

	// 获得二轮投票结果
	public ArrayList<Teacher> getSecondVoteResult() {
		String sql = "select name,college,sum(secondVote) from Teacher2  group by no order by sum(secondVote) DESC";
		ArrayList<Teacher> list = new ArrayList<Teacher>();
		try {
			Connection conn;
			conn = getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql);
			ResultSet rst = pstmt.executeQuery();
			while (rst.next()) {
				Teacher te = new Teacher();
				te.setName(rst.getString("name"));
				te.setCollege(rst.getString("college"));
				te.setSecondVote(rst.getInt("sum(secondVote)"));
				list.add(te);
			}
			pstmt.close();
			conn.close();
			return list;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	// 导出二轮投票信息
	public HSSFWorkbook ExportSecondVote() {
		ArrayList<Teacher> list = new ArrayList<Teacher>();
		list = getSecondVoteResult();
		HSSFWorkbook excel = new HSSFWorkbook();
		HSSFSheet hssfSheet = excel.createSheet("二轮结果");
		HSSFRow firstRow = hssfSheet.createRow(0);
		HSSFCell hssfCell = firstRow.createCell(0);
		hssfCell.setCellValue("姓名");
		hssfCell = firstRow.createCell(1);
		hssfCell.setCellValue("学院");
		hssfCell = firstRow.createCell(2);
		hssfCell.setCellValue("票数");
		for (int row = 0; row < list.size(); row++) {
			HSSFRow hssfRow = hssfSheet.createRow(row + 1);
			HSSFCell cell = hssfRow.createCell(0);
			cell.setCellValue(list.get(row).getName());
			cell = hssfRow.createCell(1);
			cell.setCellValue(list.get(row).getCollege());
			cell = hssfRow.createCell(2);
			cell.setCellValue(list.get(row).getSecondVote());
		}
		return excel;
	}

	// 清除一轮投票结果
	public String clearOne() {
		String sql = "delete from Teacher1;";
		String message = null;
		int count = 0;
		try {
			Connection conn;
			conn = getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql);
			count = pstmt.executeUpdate();
			if (count > 0) {
				message = "清除成功";
			} else
				message = "清除失败";
			pstmt.close();
			conn.close();
			return message;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "清除失败";
		}
	}

	// 清除二轮投票结果
	public String clearTwo() {
		String sql = "delete from Teacher2;";
		String message = null;
		int count = 0;
		try {
			Connection conn;
			conn = getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql);
			count = pstmt.executeUpdate();
			if (count > 0) {
				message = "清除成功";
			} else
				message = "清除失败";
			pstmt.close();
			conn.close();
			return message;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "清除失败";
		}
	}

	// 清除学生信息
	public String clearStudent() {
		String sql = "delete from Student;";
		String message = null;
		int count = 0;
		try {
			Connection conn;
			conn = getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql);
			count = pstmt.executeUpdate();
			if (count > 0) {
				message = "清除成功";
			} else
				message = "清除失败";
			pstmt.close();
			conn.close();
			return message;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "清除失败";
		}
	}

	// 查找一轮未投学生
	public ArrayList<Student> notVoteInFirst(String college) {
		String sql = "select * from student where firstVoteInSport='' ";
		if (!college.equals(ALLCOLLEGE))
			sql += "and college=? ";

		ArrayList<Student> list = new ArrayList<Student>();
		try {
			Connection conn;
			conn = getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql);
			if (!college.equals(ALLCOLLEGE))
				pstmt.setString(1, college);
			ResultSet rst = pstmt.executeQuery();
			while (rst.next()) {
				Student stu = new Student();
				stu.setCollege(rst.getString("college"));
				stu.setNo(rst.getString("no"));
				stu.setName(rst.getString("name"));
				list.add(stu);

			}
			pstmt.close();
			conn.close();
			return list;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	// 获取二轮为投票学生信息
	public ArrayList<Student> notVoteInSecond(String college) {
		String sql = "select * from Student where secondVote<10 ";
		if (!college.equals("所有学院"))
			sql += "and college=?;";
		ArrayList<Student> list = new ArrayList<Student>();
		try {
			Connection conn;
			conn = getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql);
			if (!college.equals("所有学院"))
				pstmt.setString(1, college);
			ResultSet rst = pstmt.executeQuery();
			while (rst.next()) {
				Student stu = new Student();
				stu.setCollege(rst.getString("college"));
				stu.setName(rst.getString("name"));
				stu.setNo(rst.getString("no"));
				list.add(stu);
			}

			pstmt.close();
			conn.close();
			return list;

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	// 导出二轮未投票学生信息
	public HSSFWorkbook ExportNotVoteInSecond(String college) {
		ArrayList<Student> list = new ArrayList<Student>();
		list = notVoteInSecond(college);
		HSSFWorkbook excel = new HSSFWorkbook();
		HSSFSheet hssfSheet = excel.createSheet("二轮未投票结果");
		HSSFRow firstRow = hssfSheet.createRow(0);
		HSSFCell hssfCell = firstRow.createCell(0);
		hssfCell.setCellValue("姓名");
		hssfCell = firstRow.createCell(1);
		hssfCell.setCellValue("学院");
		hssfCell = firstRow.createCell(2);
		hssfCell.setCellValue("学号");
		for (int row = 0; row < list.size(); row++) {
			HSSFRow hssfRow = hssfSheet.createRow(row + 1);
			HSSFCell cell = hssfRow.createCell(0);
			cell.setCellValue(list.get(row).getName());
			cell = hssfRow.createCell(1);
			cell.setCellValue(list.get(row).getCollege());
			cell = hssfRow.createCell(2);
			cell.setCellValue(list.get(row).getNo());
		}
		return excel;
	}
}
