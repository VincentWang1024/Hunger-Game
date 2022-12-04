package service;


import service.messages.MySerializable;

public interface FoodMessages {

  class ActionPerformed implements MySerializable {

    private static final long serialVersionUID = 1L;

    private final String description;

    public ActionPerformed(String description) {
      this.description = description;
    }

    public String getDescription() {
      return description;
    }
  }


  class GetFoodMessage implements MySerializable {
    private static final long serialVersionUID = 1L;
    private final Long userId;

    public GetFoodMessage(Long userId) {
      this.userId = userId;
    }

    public Long getUserId() {
      return userId;
    }
  }

}
