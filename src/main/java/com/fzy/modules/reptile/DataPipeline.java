package com.fzy.modules.reptile;

import com.geccocrawler.gecco.annotation.PipelineName;
import com.geccocrawler.gecco.pipeline.Pipeline;
import com.geccocrawler.gecco.request.HttpRequest;
import com.sun.tools.corba.se.idl.StringGen;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@PipelineName("dataPipeline")
public class DataPipeline implements Pipeline<DetailReptile> {


	public Connection connection=DruidConnectPool.getConnection();

	@Override
	public void process(DetailReptile detailReptile) {

		String uniNo=detailReptile.getNo();
		List<String> categoryNameList=detailReptile.getCategoryNameList();
		List<String> subjectList=detailReptile.getSubjectList();
		List<String> majorNameList=detailReptile.getMajorNameList();
		for (int i=0;i<categoryNameList.size();i++){
			String category=categoryNameList.get(i).trim();
			String subject=subjectList.get(i);
			String major=majorNameList.get(i).trim();
			if("".equals(major)){
				String sql="INSERT INTO reptile_data(uni_no,major_name,subject) VALUES ('"+uniNo+"','"+category.trim()+"','"+subject+"')";
				try {
					connection.prepareStatement(sql).execute();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}else if(major.contains(" ")){
				String[] majs=major.split(" ");
				for (String s:majs){
					String sql="INSERT INTO reptile_data(uni_no,major_name,subject) VALUES ('"+uniNo+"','"+s.trim()+"','"+subject+"')";
					try {
						connection.prepareStatement(sql).execute();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
			}else {
				String sql="INSERT INTO reptile_data(uni_no,major_name,subject) VALUES ('"+uniNo+"','"+major+"','"+subject+"')";
				try {
					connection.prepareStatement(sql).execute();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}


		}

	}
}
