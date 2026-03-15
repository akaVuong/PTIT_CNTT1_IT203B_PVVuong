import java.util.*;

// quầy bán vé cơ bản
public class bai1 {
    static class Ticket {
        String ticketId;
        String roomName;
        boolean isSold; //trạng thái đã bán hay chưa

        public Ticket(String ticketId, String roomName) {
            this.ticketId = ticketId;
            this.roomName = roomName;
            this.isSold = false;
        }
    }
    //Lớp TicketPool quản lý danh sách vé của một phòng
    static class TicketPool {
        String roomName;
        List<Ticket> tickets;

        public TicketPool(String roomName, int numberOfTickets) {
            this.roomName = roomName;
            tickets = new ArrayList<>();

            //tạo các vé A-001, A-002...
            for (int i = 1; i <= numberOfTickets; i++) {
                String id = roomName + "-" + String.format("%03d", i);
                tickets.add(new Ticket(id, roomName));
            }
        }

        //synchronized để đảm bảo chỉ 1 thread bán vé tại một thời điểm
        public synchronized Ticket sellTicket() {
            // tìm vé chưa bán
            for (Ticket t : tickets) {
                if (!t.isSold) {
                    t.isSold = true;
                    return t;
                }
            }
            // nếu hết vé
            return null;
        }

        //đếm số vé còn lại
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

    //Lớp BookingCounter đại diện cho quầy bán vé implements Runnable để chạy bằng Thread
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

        //Phương thức run() sẽ được Thread gọi
        @Override
        public void run() {
            while (true) {
                // nếu cả 2 phòng hết vé thì dừng
                if (roomA.getRemainingTickets() == 0 &&
                        roomB.getRemainingTickets() == 0) {
                    break;
                }
                //chọn ngẫu nhiên phòng A hoặc B
                int choice = random.nextInt(2);
                Ticket ticket = null;

                if (choice == 0) {
                    ticket = roomA.sellTicket();
                } else {
                    ticket = roomB.sellTicket();
                }

                // nếu bán được vé
                if (ticket != null) {
                    soldCount++;
                    System.out.println(
                            counterName + " đã bán vé " + ticket.ticketId
                    );
                }
                // sleep để mô phỏng thời gian bán vé
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        // tạo 2 phòng với 10 vé mỗi phòng
        TicketPool roomA = new TicketPool("A", 10);
        TicketPool roomB = new TicketPool("B", 10);
        // tạo 2 quầy bán vé
        BookingCounter counter1 = new BookingCounter("Quầy 1", roomA, roomB);
        BookingCounter counter2 = new BookingCounter("Quầy 2", roomA, roomB);
        // tạo thread cho mỗi quầy
        Thread t1 = new Thread(counter1);
        Thread t2 = new Thread(counter2);
        // bắt đầu chạy thread
        t1.start();
        t2.start();
        // chờ 2 thread chạy xong
        t1.join();
        t2.join();

        System.out.println("\nKẾT THÚC CHƯƠNG TRÌNH");
        System.out.println("Quầy 1 bán được: " + counter1.soldCount);
        System.out.println("Quầy 2 bán được: " + counter2.soldCount);
        System.out.println("Vé còn lại phòng A: " + roomA.getRemainingTickets());
        System.out.println("Vé còn lại phòng B: " + roomB.getRemainingTickets());
    }
}