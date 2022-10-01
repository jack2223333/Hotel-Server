package com.hotel.dao;

import com.hotel.domain.Room;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;

import java.util.List;

/**
 * @author Bittere_Gift
 */
@Mapper
public interface RoomDao {
    /**
     * 添加房间
     * @param room 房间对象
     */
    @Insert("insert into room values (null, #{hotel.id}, #{type.id}, #{position}, #{status})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void add(Room room);

    /**
     * 删除房间
     * @param id id
     * @return 如果不存在，则返回 0，否则返回 1
     */
    @Delete("delete from room where id = #{id}")
    Integer deleteById(Integer id);

    /**
     * 更改信息
     * @param room 更改后的对象信息实例
     * @return 如果更改成功，则返回 1，否则返回 0
     */
    @Update("update room set hotel_id = #{hotel.id}, type_id = #{type.id}, position = #{position}, status = #{status} where id = #{id}")
    Integer update(Room room);

    /**
     * 通过 id 更改房间的状态
     * @param id 房间 id
     * @param status 房间的状态
     * @return 如果更改成功，则返回 1，否则返回 0
     */
    @Update("update room set status = #{status} where id = #{id}")
    Integer updateStatus(Integer id, Integer status);

    /**
     * 通过 id 查询信息
     * @param id  id
     * @return 查询到的对象信息实例
     */
    @Select("select * from room where id = #{id}")
    @Results(id = "roomMap", value = {
            @Result(property = "roomId", column = "id"),
            @Result(property = "type", column = "type_id", one = @One(
                    select = "com.hotel.dao.RoomTypeDao.getById",
                    fetchType = FetchType.EAGER
            )),
            @Result(property = "position", column = "position"),
            @Result(property = "hotel.id", column = "hotel_id"),
            @Result(property = "status", column = "status"),
    })
    Room getById(Integer id);

    /**
     * 查询所有对象的信息
     * @return 所用实例的 List 集合
     */
    @Select("select * from room")
    @ResultMap("roomMap")
    List<Room> getAll();

    /**
     * 查询所有在 hotelId 酒店下的房间
     * @param hotelId 酒店 id
     * @return 所有在 hotelId 酒店下的房间
     */
    @Select("select * from room where hotel_id = #{hotelId}")
    @ResultMap("roomMap")
    List<Room> getByHotelId(Integer hotelId);

    /**
     * 查询所有 typeId 类型的房间
     * @param typeId 类型 id
     * @return 所有 typeId 类型的房间
     */
    @Select("select * from room where type_id = #{type_id}")
    @ResultMap("roomMap")
    List<Room> getByType(Integer typeId);

    /**
     * 查询在 hotelId 酒店中 typeId类型的房间
     * @param hotelId 酒店 id
     * @param typeId 类型 id
     * @return 在 hotelId 酒店中 typeId类型的房间
     */
    @Select("select * from room where hotel_id = #{hotelId} and type_id = #{typeId}")
    @ResultMap("roomMap")
    List<Room> getByHotelAndType(Integer hotelId, Integer typeId);

    /**
     * 查询在 hotelId 酒店中状态为 status 的房间
     * @param hotelId 酒店 id
     * @param status 状态
     * @return 在 hotelId 酒店中状态为 status 的房间
     */
    @Select("select * from room where hotel_id = #{hotelId} and status = #{status}")
    @ResultMap("roomMap")
    List<Room> getByHotelAndStatus(Integer hotelId, Integer status);

    /**
     * 查询在 hotelId 酒店中 typeId类型的 状态为 status 的房间
     * @param hotelId 酒店 id
     * @param typeId 类型 id
     * @param status 状态
     * @return 在 hotelId 酒店中 typeId类型的 状态为 status 的房间
     */
    @Select("select * from room where hotel_id = #{hotelId} and type_id = #{typeId} and status = #{status}")
    @ResultMap("roomMap")
    List<Room> getByHotelAndTypeAndStatus(Integer hotelId, Integer typeId, Integer status);

    /**
     * 查询在 hotelId 酒店中 position 房间
     * @param hotelId 酒店 id
     * @param position 房间位置
     * @return 在 hotelId 酒店中 position 房间
     */
    @Select("select * from room where hotel_id = #{hotelId} and position = #{position}")
    @ResultMap("roomMap")
    Room getByHotelAndPosition(Integer hotelId, String position);
}
