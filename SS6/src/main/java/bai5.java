import java.util.*;

/*
    Hệ thống bán vé xem phim online
    - 3 phòng: A, B, C
    - 5 quầy bán vé (Thread)
    - Vé có thể được giữ 5s trước khi thanh toán
    - TimeoutManager kiểm tra và trả vé khi hết hạn
*/

public class bai5 {

    static class Ticket {
        String id;
        boolean isHeld = false;     // đang được giữ
        boolean isSold = false;     // đã bán
        boolean isVIP = false;      // vé VIP

        long holdExpiryTime;        // thời gian hết hạn giữ
        Ticket(String id) {
            this.id = id;
        }
    }

    static class TicketPool {
        String roomName;
        List<Ticket> tickets = new ArrayList<>();
        TicketPool(String roomName, int capacity) {
            this.roomName = roomName;
            for (int i = 1; i <= capacity; i++) {
                tickets.add(new Ticket(roomName + "-" + String.format("%03d", i)));
            }
        }

        //Giữ vé trong 5s
        public synchronized Ticket holdTicket(boolean vip) {
            for (Ticket t : tickets) {
                if (!t.isSold && !t.isHeld) {
                    t.isHeld = true;
                    t.isVIP = vip;

                    t.holdExpiryTime = System.currentTimeMillis() + 5000;
                    return t;
                }
            }
            return null;
        }


        //Thanh toán vé đã giữ

        public synchronized boolean sellHeldTicket(Ticket ticket) {
            if (ticket != null && ticket.isHeld && !ticket.isSold) {
                ticket.isHeld = false;
                ticket.isSold = true;

                return true;
            }

            return false;
        }

        //Trả vé nếu giữ quá thời gian
        public synchronized void releaseExpiredTickets() {
            long now = System.currentTimeMillis();
            for (Ticket t : tickets) {
                if (t.isHeld && !t.isSold && now > t.holdExpiryTime) {

                    t.isHeld = false;

                    System.out.println(
                            "TimeoutManager: Vé " + t.id + " hết hạn giữ, trả lại kho"
                    );
                }
            }
        }
    }


    //Quầy bán vé
    static class BookingCounter implements Runnable {
        String name;
        List<TicketPool> pools;

        Random random = new Random();

        BookingCounter(String name, List<TicketPool> pools) {
            this.name = name;
            this.pools = pools;
        }

        @Override
        public void run() {

            while (true) {

                TicketPool pool = pools.get(random.nextInt(pools.size()));

                boolean vip = random.nextBoolean();

                Ticket t = pool.holdTicket(vip);

                if (t != null) {

                    System.out.println(
                            name + ": Đã giữ vé " + t.id +
                                    (vip ? " (VIP)" : "") +
                                    ". Thanh toán trong 5s"
                    );

                    try {
                        Thread.sleep(3000); // khách suy nghĩ
                    } catch (Exception e) {
                    }
                    boolean paid = random.nextBoolean();
                    if (paid) {
                        if (pool.sellHeldTicket(t)) {
                            System.out.println(
                                    name + ": Thanh toán thành công " + t.id
                            );
                        }
                    } else {
                        System.out.println(
                                name + ": Khách không thanh toán " + t.id
                        );
                    }

                } else {
                    System.out.println(
                            name + ": Hết vé phòng " + pool.roomName
                    );
                }

                try {
                    Thread.sleep(1000);
                } catch (Exception e) {
                }
            }
        }
    }

    //Thread theo dõi timeout
    static class TimeoutManager implements Runnable {
        List<TicketPool> pools;
        TimeoutManager(List<TicketPool> pools) {
            this.pools = pools;
        }

        @Override
        public void run() {

            while (true) {

                for (TicketPool p : pools) {

                    p.releaseExpiredTickets();
                }

                try {
                    Thread.sleep(1000);
                } catch (Exception e) {
                }
            }
        }
    }

    public static void main(String[] args) {
        TicketPool roomA = new TicketPool("A", 5);
        TicketPool roomB = new TicketPool("B", 5);
        TicketPool roomC = new TicketPool("C", 5);
        List<TicketPool> pools = Arrays.asList(roomA, roomB, roomC);

        // tạo 5 quầy
        for (int i = 1; i <= 5; i++) {

            Thread t = new Thread(
                    new BookingCounter("Quầy " + i, pools)
            );

            t.start();
        }

        // chạy timeout manager
        Thread timeoutThread = new Thread(new TimeoutManager(pools));
        timeoutThread.start();
    }
}