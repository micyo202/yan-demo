package com.yan.junit;

import java.io.InputStream;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.junit.Test;

public class Dom4jTest {

	@SuppressWarnings("unchecked")
	@Test
	public void test() {
		try {
			// 获取需要读取的xml文件路径
			InputStream inputStream = this.getClass().getResourceAsStream("/adminMenu.xml");
			// 创建SAXReader的对象reader
			SAXReader reader = new SAXReader();
			// 通过reader对象的read方法加载adminMenu.xml文件,获取docuemnt对象。
			Document document = reader.read(inputStream);
			// 通过document对象获取根节点element
			Element element = document.getRootElement();
			List<Element> menus = element.elements();
			for (Element menu : menus) {
				System.out.println(menu.attributeValue("name") + "--" + menu.attributeValue("ico"));
				List<Element> menuItems = menu.elements();
				for (Element menuItem : menuItems) {
					System.out.println(menuItem.elementText("name") + "------" + menuItem.elementText("url"));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void test1() {
		
		String aa = "common_user_201709111505093847834.m";
		System.out.println(aa.substring(aa.lastIndexOf("_")+1));
				
	}

}
