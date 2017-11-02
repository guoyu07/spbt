package cn.forgeeks.spring.test;

public class Chinese implements Person {

	private Axe axe;

	// 设值注入所需的setter方法
	public void setAxe(Axe axe) {
		this.axe = axe;
	}

	// 实现Perosn接口的useAxe()方法
	@Override
	public void useAxe() {
		// 调用axe的chop()方法
		// 表明Person对象依赖axe对象
		System.out.println(axe.chop());
	}

	// 构造注入所需的带参数的构造器
	public Chinese(Axe axe) {
		this.axe = axe;
	}

	public Chinese() {
		super();
	}

}
