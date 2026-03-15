import java.util.*;

/*
    Demo Deadlock khi bán combo vé (A + B)
    Combo cần 2 vé: 1 từ phòng A và 1 từ phòng B
    Nếu lock khác thứ tự → deadlock
*/

public class bai4 {
    static class Ticket {
        String id;
        boolean sold = false;

        Ticket(String id) {
            this.id = id;
        }
    }

    static class TicketPool {
        String roomName;
        List<Ticket> tickets = new ArrayList<>();

        TicketPool(String roomName, int n) {
            this.roomName = roomName;

            for (int i = 1; i <= n; i++) {
                tickets.add(new Ticket(roomName + "-" + String.format("%03d", i)));
            }
        }
        // lấy 1 vé chưa bán
        public Ticket getTicket() {

            for (Ticket t : tickets) {
                if (!t.sold) {
                    t.sold = true;
                    return t;
                }
            }
            return null;
        }
    }


    static class BookingCounter implements Runnable {
        String name;
        TicketPool roomA;
        TicketPool roomB;

        boolean lockAFirst; // xác định thứ tự lock

        BookingCounter(String name, TicketPool A, TicketPool B, boolean lockAFirst) {
            this.name = name;
            this.roomA = A;
            this.roomB = B;
            this.lockAFirst = lockAFirst;
        }

        /*
            Bán combo (A + B)
         */
        public void sellCombo() {
            if (lockAFirst) {
                synchronized (roomA) {
                    System.out.println(name + ": Đã khóa phòng A");
                    sleep();
                    synchronized (roomB) {
                        System.out.println(name + ": Đã khóa phòng B");
                        Ticket a = roomA.getTicket();
                        Ticket b = roomB.getTicket();

                        if (a != null && b != null) {
                            System.out.println(name + " bán combo thành công: "
                                    + a.id + " & " + b.id);
                        } else {
                            System.out.println(name + ": Không đủ vé combo");
                        }
                    }
                }

            } else {
                synchronized (roomB) {
                    System.out.println(name + ": Đã khóa phòng B");
                    sleep();
                    synchronized (roomA) {
                        System.out.println(name + ": Đã khóa phòng A");

                        Ticket a = roomA.getTicket();
                        Ticket b = roomB.getTicket();

                        if (a != null && b != null) {
                            System.out.println(name + " bán combo thành công: "
                                    + a.id + " & " + b.id);
                        } else {
                            System.out.println(name + ": Không đủ vé combo");
                        }
                    }
                }
            }
        }

        void sleep() {
            try {
                Thread.sleep(1000);
            } catch (Exception e) {
            }
        }

        @Override
        public void run() {
            while (true) {
                sellCombo();
                sleep();
            }
        }
    }

    public static void main(String[] args) {
        TicketPool roomA = new TicketPool("A", 5);
        TicketPool roomB = new TicketPool("B", 5);
        /*
            Quầy 1: lock A → B
            Quầy 2: lock B → A
            → gây DEADLOCK
        */
        BookingCounter counter1 = new BookingCounter("Quầy 1", roomA, roomB, true);
        BookingCounter counter2 = new BookingCounter("Quầy 2", roomA, roomB, false);
        Thread t1 = new Thread(counter1);
        Thread t2 = new Thread(counter2);

        t1.start();
        t2.start();
    }
}