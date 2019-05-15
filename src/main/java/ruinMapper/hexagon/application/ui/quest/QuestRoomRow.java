package ruinMapper.hexagon.application.ui.quest;

import ruinMapper.hexagon.domain.room.RoomPort;

public class QuestRoomRow {

    private RoomPort room;

    private String areaName;
    private String coordinates;
    private String roomName;

    public QuestRoomRow(RoomPort room) {
        this.room = room;
        areaName = room.accessArea().accessTitle();
        coordinates = "X: " + room
                .accessCoordinates().x + " Y: " +
                room.accessCoordinates().y;
        roomName = room.accessTitle();
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public String getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(String coordinates) {
        this.coordinates = coordinates;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }
}
