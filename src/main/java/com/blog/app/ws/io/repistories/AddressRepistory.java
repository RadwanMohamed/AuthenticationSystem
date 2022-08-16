package com.blog.app.ws.io.repistories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.blog.app.ws.io.entity.AddressEntity;
import com.blog.app.ws.io.entity.UserEntity;

@Repository
public interface AddressRepistory extends CrudRepository<AddressEntity, Long> {
	List<AddressEntity> findAllByUserDetails(UserEntity userEntity);
	AddressEntity findByAddressId(String addressId);
}
