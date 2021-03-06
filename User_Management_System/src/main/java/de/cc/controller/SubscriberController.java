package de.cc.controller;

import de.cc.Subscriber;
import de.cc.contract.Budget;
import de.cc.contract.Business;
import de.cc.contract.Contract;
import de.cc.contract.Premium;
import de.cc.phone.FeaturePhone;
import de.cc.phone.Phone;
import de.cc.phone.SimpleSmartPhone;
import de.cc.phone.SuperSmartPhone;

public class SubscriberController {

	private Controller controller;

	public SubscriberController(Controller controller) {
		this.controller = controller;
	}

	public void printSubscriberManagementMenu() {
		System.out.println("(1) Add Subscriber");
		System.out.println("(2) Edit Subscriber");
		System.out.println("(3) Remove Subscriber");
		System.out.println("(4) List Subscribers");
		System.out.println("(5) Return");

		switch (controller.readInt()) {
		case 1:
			addSubscriber();
			break;
		case 2:
			editSubscriber();
			break;
		case 3:
			removeSubscriber();
			break;
		case 4:
			controller.listSubscribers();
			controller.printStartMenu();
			break;
		case 5:
			controller.printStartMenu();
			break;
		default:
			System.out.println("Illegal Input");
			this.printSubscriberManagementMenu();
		}
	}

	public void addSubscriber() {
		try {
			Subscriber sub = new Subscriber();

			System.out.println("Subscriber Name:");
			sub.setName(inputName());

			System.out.println("Subscriber MSIN:");
			sub.setId(inputMSIN());

			System.out.println("Subscriber Terminal Type:");
			sub.setPhone(inputTerminalType());

			System.out.println("Subscriber Subscription Type:");
			sub.setContract(inputSubscriptionType());

			controller.getSubscribers().add(sub);
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		}

		controller.printStartMenu();

	}
	
	public String inputMSIN() {
		System.out.print("Enter MSIN: ");
		String msin = controller.readString();
		if (msin == null || !msin.matches("\\d{10}")) {
			System.out.println("Illegal Input. Only a single ten-digit-number is allowed. Try again.");
			return inputMSIN();
		}
		for (Subscriber sub: controller.getSubscribers()) {
			if (sub.getId().equals(msin)) {
				System.out.println("MSIN is already in use. Enter another one.");
				return inputMSIN();
			}
		}
		return msin;
	}
	
	public String inputName() {
		System.out.print("Enter Name: ");
		String name = controller.readString();
		if (name == null || name.isEmpty()) {
			System.out.println("Illegal Input. Name cannot be empty. Try again.");
			return inputName();
		}
		return name;
	}

	public Phone inputTerminalType() {
		System.out.println("(1) FeaturePhone");
		System.out.println("(2) SimpleSmartPhone");
		System.out.println("(3) SuperSmartPhone");

		switch (controller.readInt()) {
		case 1:
			return new FeaturePhone();
		case 2:
			return new SimpleSmartPhone();
		case 3:
			return new SuperSmartPhone();
		default:
			System.out.println("Illegal Input");
			return this.inputTerminalType();
		}
	}

	public Contract inputSubscriptionType() {
		System.out.println("(1) Budget");
		System.out.println("(2) Business");
		System.out.println("(3) Premium");

		switch (controller.readInt()) {
		case 1:
			return new Budget();
		case 2:
			return new Business();
		case 3:
			return new Premium();
		default:
			System.out.println("Illegal Input");
			return this.inputSubscriptionType();
		}
	}

	public void removeSubscriber() {
		try {
			controller.getSubscribers().remove(controller.searchSubscriber());
		} catch (IllegalStateException e) {
			System.out.println(e.getMessage());
		}

		controller.printStartMenu();

	}

	public void editSubscriber() {

		try {
			int index = controller.searchSubscriber();

			Subscriber sub = controller.getSubscribers().get(index);

			System.out.println("(1) Change Name");
			System.out.println("(2) Change Terminal");
			System.out.println("(3) Change Subscription");
			System.out.println("(4) Return");

			switch (controller.readInt()) {
			case 1:
				System.out.print("Subscriber Name (" + sub.getName() + "): ");
				sub.setName(inputName());
				editSubscriber();
			case 2:
				System.out.println("(" + sub.getPhone() + ")");
				System.out.println("Subscriber Terminal Type: ");
				sub.setPhone(inputTerminalType());
				editSubscriber();
			case 3:
				System.out.println("(" + sub.getContract() + ")");
				System.out.println("Subscriber Subscription Type: ");
				sub.setContract(inputSubscriptionType());
				editSubscriber();
			case 4:
				printSubscriberManagementMenu();
			default:
				System.out.println("Illegal Input");
				printSubscriberManagementMenu();
			}

		} catch (Exception e) {
			this.printSubscriberManagementMenu();
		}
	}

	public void listSubscribers() {
		for (Subscriber sub : controller.getSubscribers()) {
			System.out.println(sub);
		}
		controller.printStartMenu();
	}

}
