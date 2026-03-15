import java.util.*;

//Bài K3: Sử dụng wait() và notifyAll(), Khi hết vé: quầy bán vé sẽ wait(), Khi nhà cung cấp thêm vé: notifyAll() đánh thức các quầy

public class bai3 {
    static class Ticket {
        String ticketId;
        String roomName;
        boolean isSold;

        public Ticket(String ticketId, String roomName) {
            this.ticketId = ticketId;
            this.roomName = roomName;
            this.isSold = false;
        }
    }


    static class TicketPool {
        String roomName;
        List<Ticket> tickets = new ArrayList<>();
        public TicketPool(String roomName, int numberOfTickets) {
            this.roomName = roomName;

            for (int i = 1; i <= numberOfTickets; i++) {
                String id = roomName + "-" + String.format("%03d", i);
                tickets.add(new Ticket(id, roomName));
            }
        }

        /*
            Bán vé
            Nếu hết vé → wait()
            Khi có vé mới → tiếp tục bán
         */
        public synchronized Ticket sellTicket() {
            while (true) {
                // tìm vé chưa bán
                for (Ticket t : tickets) {
                    if (!t.isSold) {
                        t.isSold = true;
                        return t;
                    }
                }

                // nếu không còn vé
                try {
                    System.out.println("Hết vé phòng " + roomName + ", đang chờ...");
                    wait(); // chờ nhà cung cấp thêm vé
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        public synchronized void addTickets(int count) {
            int currentSize = tickets.size();
            for (int i = 1; i <= count; i++) {
                int number = currentSize + i;
                String id = roomName + "-" + String.format("%03d", number);
                tickets.add(new Ticket(id, roomName));
            }

            System.out.println("Nhà cung cấp: Đã thêm " + count + " vé vào phòng " + roomName);
            // đánh thức tất cả thread đang chờ
            notifyAll();
        }

        // đếm vé còn lại
        public synchronized int getRemainingTickets() {
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
            while (true) {
                int choice = random.nextInt(2);
                Ticket ticket;
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

    //Lớp nhà cung cấp vé
    static class TicketSupplier implements Runnable {
        TicketPool roomA;
        TicketPool roomB;
        int supplyCount;
        int interval;
        int rounds;

        public TicketSupplier(TicketPool roomA, TicketPool roomB,
                              int supplyCount, int interval, int rounds) {
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

                roomA.addTickets(supplyCount);
                roomB.addTickets(supplyCount);
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        TicketPool roomA = new TicketPool("A", 10);
        TicketPool roomB = new TicketPool("B", 10);
        BookingCounter counter1 = new BookingCounter("Quầy 1", roomA, roomB);
        BookingCounter counter2 = new BookingCounter("Quầy 2", roomA, roomB);
        TicketSupplier supplier = new TicketSupplier(
                roomA,
                roomB,
                3,
                3000,
                3
        );

        Thread t1 = new Thread(counter1);
        Thread t2 = new Thread(counter2);
        Thread supplierThread = new Thread(supplier);

        t1.start();
        t2.start();
        supplierThread.start();

        Thread.sleep(20000);
        System.out.println("\nKẾT THÚC");
        System.out.println("Quầy 1 bán được: " + counter1.soldCount);
        System.out.println("Quầy 2 bán được: " + counter2.soldCount);
        System.exit(0);
    }
}