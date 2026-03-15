import java.util.*;
// cung cấp vé định kì
public class bai2 {
    static class Ticket {
        String ticketId;   // mã vé
        String roomName;   // phòng
        boolean isSold;    // trạng thái vé

        public Ticket(String ticketId, String roomName) {
            this.ticketId = ticketId;
            this.roomName = roomName;
            this.isSold = false;
        }
    }

    static class TicketPool {
        String roomName;
        List<Ticket> tickets;

        public TicketPool(String roomName, int numberOfTickets) {
            this.roomName = roomName;
            tickets = new ArrayList<>();

            // tạo vé ban đầu
            for (int i = 1; i <= numberOfTickets; i++) {
                String id = roomName + "-" + String.format("%03d", i);
                tickets.add(new Ticket(id, roomName));
            }
        }


        public synchronized Ticket sellTicket() {
            for (Ticket t : tickets) {
                if (!t.isSold) {
                    t.isSold = true;
                    return t;
                }
            }

            return null;
        }

        public synchronized void addTickets(int count) {
            int currentSize = tickets.size();
            for (int i = 1; i <= count; i++) {
                int number = currentSize + i;
                String id = roomName + "-" + String.format("%03d", number);
                tickets.add(new Ticket(id, roomName));
            }

            System.out.println("Nhà cung cấp: Đã thêm " + count + " vé vào phòng " + roomName);
        }

        // đếm vé còn lại
        public int getRemainingTickets() {
            int count = 0;
            for (Ticket t : tickets) {
                if (!t.isSold) {
                    count++;
                }
            }
            return count;
        }
    }


    static class BookingCounter implements Runnable {
        String counterName;
        TicketPool roomA;
        TicketPool roomB;
        int soldCount = 0;
        Random random = new Random();

        public BookingCounter(String counterName, TicketPool roomA, TicketPool roomB) {
            this.counterName = counterName;
            this.roomA = roomA;
            this.roomB = roomB;
        }

        @Override
        public void run() {
            // chạy trong thời gian dài
            while (true) {
                int choice = random.nextInt(2);
                Ticket ticket = null;
                if (choice == 0) {
                    ticket = roomA.sellTicket();
                } else {
                    ticket = roomB.sellTicket();
                }
                if (ticket != null) {
                    soldCount++;
                    System.out.println(counterName + " đã bán vé " + ticket.ticketId);
                }
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    static class TicketSupplier implements Runnable {
        TicketPool roomA;
        TicketPool roomB;
        int supplyCount;  // số vé thêm mỗi lần
        int interval;     // thời gian chờ (ms)
        int rounds;       // số lần thêm vé

        public TicketSupplier(TicketPool roomA, TicketPool roomB, int supplyCount, int interval, int rounds) {
            this.roomA = roomA;
            this.roomB = roomB;
            this.supplyCount = supplyCount;
            this.interval = interval;
            this.rounds = rounds;
        }

        @Override
        public void run() {

            for (int i = 0; i < rounds; i++) {

                try {
                    Thread.sleep(interval);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                // thêm vé vào mỗi phòng
                roomA.addTickets(supplyCount);
                roomB.addTickets(supplyCount);
            }
        }
    }

    /*
        Hàm main chạy chương trình
     */
    public static void main(String[] args) throws InterruptedException {

        // tạo 2 phòng ban đầu mỗi phòng 10 vé
        TicketPool roomA = new TicketPool("A", 10);
        TicketPool roomB = new TicketPool("B", 10);

        // tạo 2 quầy bán vé
        BookingCounter counter1 = new BookingCounter("Quầy 1", roomA, roomB);
        BookingCounter counter2 = new BookingCounter("Quầy 2", roomA, roomB);

        // tạo nhà cung cấp vé
        TicketSupplier supplier = new TicketSupplier(
                roomA,
                roomB,
                3,      // mỗi lần thêm 3 vé
                3000,   // 3 giây
                3       // số lần thêm
        );

        Thread t1 = new Thread(counter1);
        Thread t2 = new Thread(counter2);
        Thread supplierThread = new Thread(supplier);

        // start các thread
        t1.start();
        t2.start();
        supplierThread.start();
        // chạy trong 15 giây
        Thread.sleep(15000);

        System.out.println("\nKẾT THÚC CHƯƠNG TRÌNH");
        System.out.println("Quầy 1 bán được: " + counter1.soldCount);
        System.out.println("Quầy 2 bán được: " + counter2.soldCount);
        System.out.println("Vé còn lại phòng A: " + roomA.getRemainingTickets());
        System.out.println("Vé còn lại phòng B: " + roomB.getRemainingTickets());
        System.exit(0);
    }
}