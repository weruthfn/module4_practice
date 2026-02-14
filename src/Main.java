import java.util.Scanner;
interface Document {
    void open();
    String getType();
}
class Report implements Document {
    private String title;
    private String author;
    public Report(String title, String author) {
        this.title = title;
        this.author = author;
    }
    @Override
    public void open() {
        System.out.println(" Открытие отчета: " + title);
        System.out.println(" Автор: " + author);
    }
    @Override
    public String getType() {
        return "Report";
    }
}
class Resume implements Document {
    private String name;
    private int experience;
    public Resume(String name, int experience) {
        this.name = name;
        this.experience = experience;
    }
    @Override
    public void open() {
        System.out.println("Открытие резюме: " + name);
        System.out.println("Опыт работы: " + experience + " лет");
        System.out.println("Навыки: Java, Git, SQL");
    }
    @Override
    public String getType() {
        return "Resume";
    }
}
class Letter implements Document {
    private String recipient;
    private String subject;
    public Letter(String recipient, String subject) {
        this.recipient = recipient;
        this.subject = subject;
    }
    @Override
    public void open() {
        System.out.println("Открытие письма для: " + recipient);
        System.out.println("Тема: " + subject);
        System.out.println("Текст: Уважаемый " + recipient + "...");
    }
    @Override
    public String getType() {
        return "Letter";
    }
}
class Invoice implements Document {
    private String client;
    private double amount;
    private String invoiceNumber;
    public Invoice(String client, double amount, String invoiceNumber) {
        this.client = client;
        this.amount = amount;
        this.invoiceNumber = invoiceNumber;
    }
    @Override
    public void open() {
        System.out.println("Открытие счета №" + invoiceNumber);
        System.out.println("клиент: " + client);
        System.out.println("Сумма: " + amount);
        System.out.println("Статус: ожидает оплаты");
    }
    @Override
    public String getType() {
        return "Invoice";
    }
}
abstract class DocumentCreator {
    public abstract Document createDocument();
    public abstract Document createDocumentWithParams(Object... params);
    public void demonstrateDocument() {
        Document doc = createDocument();
        System.out.println("\n=== итог ===");
        System.out.println("Тип: " + doc.getType());
        doc.open();
        System.out.println("===================\n");
    }
}
class ReportCreator extends DocumentCreator {
    @Override
    public Document createDocument() {
        return new Report("Годовой отчет", "Садикжанов");
    }
    @Override
    public Document createDocumentWithParams(Object... params) {
        if (params.length >= 2) {
            return new Report((String)params[0], (String)params[1]);
        }
        return createDocument();
    }
}
class ResumeCreator extends DocumentCreator {
    @Override
    public Document createDocument() {
        return new Resume("Белялов", 5);
    }
    @Override
    public Document createDocumentWithParams(Object... params) {
        if (params.length >= 2) {
            return new Resume((String)params[0], (Integer)params[1]);
        }
        return createDocument();
    }
}
class LetterCreator extends DocumentCreator {
    @Override
    public Document createDocument() {
        return new Letter("Цой", "Встреча");
    }
    @Override
    public Document createDocumentWithParams(Object... params) {
        if (params.length >= 2) {
            return new Letter((String)params[0], (String)params[1]);
        }
        return createDocument();
    }
}
class InvoiceCreator extends DocumentCreator {
    @Override
    public Document createDocument() {
        return new Invoice("ООО Сатпаев университет", 5000, "INV-001");
    }
    @Override
    public Document createDocumentWithParams(Object... params) {
        if (params.length >= 3) {
            return new Invoice((String)params[0], (Double)params[1], (String)params[2]);
        }
        return createDocument();
    }
}
public class Main {
    private static Scanner scanner = new Scanner(System.in);
    public static void main(String[] args) {
        System.out.println("=== система документов===\n");
        while (true) {
            showMenu();
            int choice = getUserChoice();
            if (choice == 0) {
                System.out.println("завершение.");
                break;
            }
            processChoice(choice);
        }

        scanner.close();
    }
    private static void showMenu() {
        System.out.println("\nВыберите тип документа:");
        System.out.println("1.Отчет Report");
        System.out.println("2.Резюме Resume");
        System.out.println("3.Письмо Letter");
        System.out.println("4.Счет Invoice - НОВЫЙ ТИП");
        System.out.println("5.Демонстрация всех типов");
        System.out.println("0.Выход");
        System.out.print("Ваш выбор: ");
    }
    private static int getUserChoice() {
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            return -1;
        }
    }
    private static void processChoice(int choice) {
        switch (choice) {
            case 1:
                createReport();
                break;
            case 2:
                createResume();
                break;
            case 3:
                createLetter();
                break;
            case 4:
                createInvoice();
                break;
            case 5:
                demonstrateAll();
                break;
            default:
                System.out.println("Неверный выбор!");
        }
    }
    private static void createReport() {
        System.out.println("\n--- СОЗДАНИЕ ОТЧЕТА ---");
        System.out.print("Название: ");
        String title = scanner.nextLine();
        System.out.print("Автор: ");
        String author = scanner.nextLine();
        DocumentCreator creator = new ReportCreator();
        Document doc = creator.createDocumentWithParams(title, author);

        System.out.println("\nДокумент создан!");
        doc.open();
    }
    private static void createResume() {
        System.out.println("\n--- СОЗДАНИЕ РЕЗЮМЕ ---");
        System.out.print("Имя: ");
        String name = scanner.nextLine();
        System.out.print("Опыт (лет): ");
        int exp = scanner.nextInt();
        scanner.nextLine();
        DocumentCreator creator = new ResumeCreator();
        Document doc = creator.createDocumentWithParams(name, exp);
        System.out.println("\nДокумент создан!");
        doc.open();
    }
    private static void createLetter() {
        System.out.println("\n--- СОЗДАНИЕ ПИСЬМА ---");
        System.out.print("Получатель: ");
        String recipient = scanner.nextLine();
        System.out.print("Тема: ");
        String subject = scanner.nextLine();
        DocumentCreator creator = new LetterCreator();
        Document doc = creator.createDocumentWithParams(recipient, subject);
        System.out.println("\nДокумент создан!");
        doc.open();
    }
    private static void createInvoice() {
        System.out.println("\n--- СОЗДАНИЕ СЧЕТА ---");
        System.out.print("Клиент: ");
        String client = scanner.nextLine();
        System.out.print("Сумма: ");
        double amount = scanner.nextDouble();
        scanner.nextLine();
        System.out.print("Номер счета: ");
        String number = scanner.nextLine();

        DocumentCreator creator = new InvoiceCreator();
        Document doc = creator.createDocumentWithParams(client, amount, number);

        System.out.println("\nДокумент создан!");
        doc.open();
    }
    private static void demonstrateAll() {
        System.out.println("\n=== все типы !!! ===");
        DocumentCreator[] creators = {
                new ReportCreator(),
                new ResumeCreator(),
                new LetterCreator(),
                new InvoiceCreator()
        };
        for (DocumentCreator creator : creators) {
            creator.demonstrateDocument();
        }
    }
}