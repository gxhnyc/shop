package shop.entity;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class Cellphone {
	private String cp_id;
	
	@NotEmpty(message="必填")
	private String cp_brand;
	@Size(min=2,max=128)
	private String cp_model;
	@Size(min=2,max=128)
	private String cp_os;
	@Size(min=2,max=128)
	private String cp_cpu;
	private Integer cp_cpu_cores;//cpu内核数量cp_cpu_cores
	@Min(1)
	private Integer cp_ram;//以G为单位
	@Min(2)
	private Integer cp_storage;//以G为单位
	@Size(min=2,max=128)
	private String cp_color;
	@Size(max=1024)
	private String cp_description;
	@Min(100)
	private Integer cp_price;//单位：元
	public String getCp_id() {
		return cp_id;
	}
	public void setCp_id(String cp_id) {
		this.cp_id = cp_id;
	}
	public String getCp_brand() {
		return cp_brand;
	}
	public void setCp_brand(String cp_brand) {
		if(cp_brand!=null &&!cp_brand.trim().isEmpty()) {
			this.cp_brand = cp_brand;
		}
		
	}
	public String getCp_model() {
		
		return cp_model;
	}
	public void setCp_model(String cp_model) {
		if(cp_model!=null&&!cp_model.trim().isEmpty()) {
			this.cp_model = cp_model;
				}
		
	}
	public String getCp_os() {
		return cp_os;
	}
	public void setCp_os(String cp_os) {
		if(cp_os!=null&&!cp_os.trim().isEmpty()) {
			this.cp_os = cp_os;
		}
		
	}
	public String getCp_cpu() {
		return cp_cpu;
	}
	public void setCp_cpu(String cp_cpu) {
		this.cp_cpu = cp_cpu;
	}
	public Integer getCp_ram() {
		return cp_ram;
	}
	public void setCp_ram(Integer cp_ram) {
		this.cp_ram = cp_ram;
	}
	public Integer getCp_storage() {
		return cp_storage;
	}
	public void setCp_storage(Integer cp_storage) {
		this.cp_storage = cp_storage;
	}
	public String getCp_color() {
		return cp_color;
	}
	public void setCp_color(String cp_color) {
		this.cp_color = cp_color;
	}
	public String getCp_description() {
		return cp_description;
	}
	public void setCp_description(String cp_description) {
		this.cp_description = cp_description;
	}
	public Integer getCp_price() {
		return cp_price;
	}
	public void setCp_price(Integer cp_price) {
		this.cp_price = cp_price;
	}
	public Integer getCp_cpu_cores() {
		return cp_cpu_cores;
	}
	public void setCp_cpu_cores(Integer cp_cpu_cores) {
		this.cp_cpu_cores = cp_cpu_cores;
	}
	@Override
	public String toString() {
		return "Cellphone [cp_id=" + cp_id + ", cp_brand=" + cp_brand + ", cp_model=" + cp_model + ", cp_os=" + cp_os
				+ ", cp_cpu=" + cp_cpu + ", cp_cpu_cores=" + cp_cpu_cores + ", cp_ram=" + cp_ram + ", cp_storage="
				+ cp_storage + ", cp_color=" + cp_color + ", cp_description=" + cp_description + ", cp_price="
				+ cp_price + "]";
	}
	
	
	
}
