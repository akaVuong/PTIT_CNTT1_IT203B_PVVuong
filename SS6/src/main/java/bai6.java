import java.util.*;
import java.util.concurrent.*;
import java.lang.management.*;

public class bai6 {
    static class Ticket {
        String id;
        boolean sold = false;
        Ticket(String id) {
            this.id = id;
        }
    }

    static class TicketPool {
        String room;
        List<Ticket> tickets = new ArrayList<>();
        TicketPool(String room, int capacity) {
            this.room = room;

            for (int i = 1; i <= capacity; i++) {
                tickets.add(new Ticket(room + "-" + String.format("%03d", i)));
            }
        }

        synchronized Ticket sellTicket() {
            for (Ticket t : tickets) {
                if (!t.sold) {
                    t.sold = true;
                    return t;
                }
            }

            return null;
        }

        synchronized void addTickets(int n) {
            int size = tickets.size();
            for (int i = 1; i <= n; i++) {

                int number = size + i;

                tickets.add(
                        new Ticket(room + "-" + String.format("%03d", number))
                );
            }

            System.out.println("Đã thêm " + n + " vé vào phòng " + room);
        }

        int soldCount() {
            int c = 0;
            for (Ticket t : tickets)
                if (t.sold) c++;
            return c;
        }
    }

    static class BookingCounter implements Runnable {
        String name;
        List<TicketPool> pools;

        static volatile boolean running = true;

        Random random = new Random();

        BookingCounter(String name, List<TicketPool> pools) {
            this.name = name;
            this.pools = pools;
        }

        public void run() {
            while (true) {
                if (!running) {
                    try {
                        Thread.sleep(500);
                    } catch (Exception e) {}

                    continue;
                }

                TicketPool p = pools.get(random.nextInt(pools.size()));
                Ticket t = p.sellTicket();
                if (t != null) {

                    System.out.println(name + " bán vé " + t.id);
                }

                try {
                    Thread.sleep(500);
                } catch (Exception e) {}
            }
        }
    }

    static class DeadlockDetector implements Runnable {
        public void run() {
            try {
                Thread.sleep(2000);
                ThreadMXBean bean = ManagementFactory.getThreadMXBean();
                long[] ids = bean.findDeadlockedThreads();

                System.out.println("Đang quét deadlock...");

                if (ids != null) {

                    System.out.println("Phát hiện DEADLOCK!");

                } else {

                    System.out.println("Không phát hiện deadlock.");
                }

            } catch (Exception e) {}
        }
    }

    static void showStats(List<TicketPool> pools) {
        System.out.println("\n=THỐNG KÊ=");
        int total = 0;
        for (TicketPool p : pools) {

            int sold = p.soldCount();

            System.out.println("Phòng " + p.room + ": bán " + sold + " vé");

            total += sold;
        }

        System.out.println("Tổng vé đã bán: " + total);
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        ExecutorService executor = null;
        List<TicketPool> pools = new ArrayList<>();

        while (true) {
            System.out.println("\n===== MENU =====");
            System.out.println("1. Bắt đầu mô phỏng");
            System.out.println("2. Tạm dừng");
            System.out.println("3. Tiếp tục");
            System.out.println("4. Thêm vé");
            System.out.println("5. Xem thống kê");
            System.out.println("6. Phát hiện deadlock");
            System.out.println("7. Thoát");
            int choice = sc.nextInt();
            switch (choice) {
                case 1:
                    System.out.print("Số phòng: ");
                    int rooms = sc.nextInt();

                    System.out.print("Số vé/phòng: ");
                    int cap = sc.nextInt();

                    System.out.print("Số quầy: ");
                    int counters = sc.nextInt();

                    pools.clear();

                    for (int i = 0; i < rooms; i++) {

                        char roomName = (char) ('A' + i);

                        pools.add(new TicketPool("" + roomName, cap));
                    }

                    executor = Executors.newFixedThreadPool(counters);

                    for (int i = 1; i <= counters; i++) {

                        executor.submit(
                                new BookingCounter("Quầy " + i, pools)
                        );
                    }

                    System.out.println("Hệ thống đã khởi động.");

                    break;

                case 2:
                    BookingCounter.running = false;
                    System.out.println("Đã tạm dừng.");
                    break;

                case 3:
                    BookingCounter.running = true;
                    System.out.println("Đã tiếp tục.");
                    break;

                case 4:
                    System.out.print("Chọn phòng (A,B,C..): ");
                    String r = sc.next();

                    System.out.print("Số vé thêm: ");
                    int add = sc.nextInt();

                    for (TicketPool p : pools)
                        if (p.room.equalsIgnoreCase(r))
                            p.addTickets(add);

                    break;

                case 5:
                    showStats(pools);
                    break;

                case 6:
                    new Thread(new DeadlockDetector()).start();
                    break;

                case 7:
                    if (executor != null)
                        executor.shutdownNow();
                    System.out.println("Kết thúc chương trình.");
                    return;
            }
        }
    }
}