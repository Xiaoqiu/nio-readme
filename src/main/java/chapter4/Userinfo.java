package chapter4;

import lombok.Data;

import java.io.Serializable;


/**
 * @author kate
 * @create 2019/7/3
 * @since 1.0.0
 */
@Data
public class Userinfo implements Serializable {
  private long id;
  private String username;
  private String password;
}