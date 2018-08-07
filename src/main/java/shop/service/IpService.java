package shop.service;

public interface IpService {
	/**
	 * 通过IP，调用高德地图的api，返回定位所在位置
	 * @param ip
	 * @return
	 */
	String ipToProvince(String ip);
}
