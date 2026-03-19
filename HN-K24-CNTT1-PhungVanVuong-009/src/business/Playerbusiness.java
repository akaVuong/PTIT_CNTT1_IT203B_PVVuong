package business;

import entity.ChessPlayer;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Playerbusiness {
    private static Playerbusiness instance;
    private List<ChessPlayer> chessplayers;
    private Playerbusiness (){
        chessplayers = new ArrayList<>();
    }
    public static Playerbusiness getInstance() {
        if(instance == null){
            instance = new Playerbusiness();
        }
        return instance;
    }

    //hiển thị
    public void displaylist(){
        if(chessplayers.isEmpty()){
            System.out.println("danh sach rỗng");
            return;
        }
        System.out.println("");
        chessplayers.forEach(ChessPlayer::displayData);
    }

    //thêm
    public void addPlayer(ChessPlayer player){
        boolean exists = chessplayers.stream().anyMatch(u -> u.getPlayerId().equals(player.getPlayerId()));
        if(exists){
            System.out.println("cờ thủ đã tồn tại");
        }else {
            chessplayers.add(player);
            System.out.println("thêm thành công cờ thử");
        }
    }


    //cập nhật


    //xóa
    public void deletePlayer(String id){
        int oldsize = chessplayers.size();
        chessplayers.removeIf(u -> u.getPlayerId().equals(id));
        if(oldsize == chessplayers.size()){
            System.out.println("mã cờ thủ không tồn tại");
        }else {
            System.out.println("xóa thành công cờ thủ");
        }
    }


    //timd kiếm
    public void searchPlayer(String keyName){
        List<ChessPlayer> result = chessplayers.stream().filter(u -> u.getPlayerName().toLowerCase().contains(keyName.toLowerCase())).collect(Collectors.toList());
        if(result.isEmpty()){
            System.out.println("không tìm thấy tên cờ thủ");
        }else {
            result.forEach(ChessPlayer::displayData);
        }
    }


    //lọc


    //sắp xếp
    public void sortPlayerByElo(){
        chessplayers = chessplayers.stream().sorted((u1, u2) -> Integer.compare(u2.getElo(), u1.getElo())).collect(Collectors.toList());
        displaylist();
    }
}
