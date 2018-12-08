package com.hzsxy.bighomework.util;
	import java.text.SimpleDateFormat;
	/**
	 * 获取20位随机数 
	 * 4位年份+13位时间戳+3位随机数
	 */
	public class GetTime {
		public static void main(String[] args) {
			//调用生成id方法
			System.out.println(getGuid()); 
		}
		
		/**
		 * 20位末尾的数字id
		 */
		public static int Guid=100;
 
		public static String getGuid() {
			
			GetTime.Guid+=1;
 
			long now = System.currentTimeMillis();  
			//获取4位年份数字  
			SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy");  
			//获取时间戳  
			String time=dateFormat.format(now);
			String info=now+"";
			//获取三位随机数  
			//int ran=(int) ((Math.random()*9+1)*100); 
			//要是一段时间内的数据连过大会有重复的情况，所以做以下修改
			int ran=0;
			if(GetTime.Guid>999){
				GetTime.Guid=100;    	
			}
			ran=GetTime.Guid;
					
			return time+info.substring(2, info.length())+ran;  
		}
}