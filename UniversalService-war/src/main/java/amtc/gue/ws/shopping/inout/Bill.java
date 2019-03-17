package amtc.gue.ws.shopping.inout;

import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import amtc.gue.ws.base.inout.User;

/**
 * JAXB object for the Bill complex type
 * 
 * @author Thomas
 *
 */
@XmlAccessorType(XmlAccessType.NONE)
@XmlType(name = "bill", propOrder = { "billId", "date", "amount", "shop", "billinggroup" })
public class Bill {
	@XmlElement(name = "billId", required = false, nillable = true)
	private String billId;
	@XmlElement(name = "date", required = false, nillable = true)
	private Date date;
	@XmlElement(name = "amount", required = false, nillable = true)
	private Double amount;
	@XmlElement(name = "websafeKey", required = false, nillable = true)
	private String websafeKey;
	@XmlElement(name = "shop", required = false, nillable = true)
	private Shop shop;
	@XmlElement(name = "billinggroup", required = false, nillable = true)
	private Billinggroup billinggroup;
	@XmlElement(name = "user", required = false, nillable = true)
	private User user;

	// Getters and Setters
	public String getBillId() {
		return billId;
	}

	public void setBillId(String billId) {
		this.billId = billId;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public String getWebsafeKey() {
		return websafeKey;
	}

	public void setWebsafeKey(String websafeKey) {
		this.websafeKey = websafeKey;
	}

	public Shop getShop() {
		return shop;
	}

	public void setShop(Shop shop) {
		this.shop = shop;
	}

	public Billinggroup getBillinggroup() {
		return billinggroup;
	}

	public void setBillinggroup(Billinggroup billinggoup) {
		this.billinggroup = billinggoup;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
}
