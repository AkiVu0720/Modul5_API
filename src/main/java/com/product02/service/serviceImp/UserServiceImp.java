package com.product02.service.serviceImp;

import com.product02.exception.CustomException;
import com.product02.exception.PasswordNotMatchException;
import com.product02.exception.UserNotFoundException;
import com.product02.model.entity.ERoles;
import com.product02.model.entity.RolesEntity;
import com.product02.model.mapper.mapperUser.MapperRegister;
import com.product02.model.mapper.mapperUser.UserMapper;
import com.product02.payload.requet.RegisterRequest;
import com.product02.payload.requet.UploadPassRequest;
import com.product02.payload.requet.UserRequest;
import com.product02.payload.response.RegisterResponse;
import com.product02.model.entity.UserEntity;
import com.product02.payload.response.UserResponse;
import com.product02.repository.UserRepository;
import com.product02.service.RolesService;
import com.product02.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserServiceImp implements UserService {

    @Lazy
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private MapperRegister mapperRegister;
    @Autowired
    private RolesService rolesService;

    @Override
    public Page<UserEntity> findAll(Pageable pageable) {

        return userRepository.findAll(pageable);
    }

    /**
     *
     * @param nameDirection kieu sort ASC hoac DESC cua name
     * @param statusDirection kieu sort ASC hoac DESC cua status
     * @return
     */
    @Override
    public Page<UserEntity> sortByNameAndUser(int page, int size,String idDirection,String nameDirection, String statusDirection) {
        List<Sort.Order> orderList = new ArrayList<>();
        Sort.Order orderId = sortByNameSort(idDirection,"id");
        orderList.add(orderId);
        Sort.Order orderName = sortByNameSort(nameDirection,"fullName");
        orderList.add(orderName);
        Sort.Order orderStatus = sortByNameSort(statusDirection,"status");
        orderList.add(orderStatus);
        Pageable pageable = PageRequest.of(page,size,Sort.by(orderList));
        return userRepository.findAll(pageable);

    }

    /**
     *
     * @param nameDirection: kieu sort ASC hoac DESC
     * @param nameSort: ten du lieu muo  sort
     * @return
     */
    public Sort.Order sortByNameSort(String nameDirection, String nameSort){
        Sort.Order orderName;
        if (nameDirection.equalsIgnoreCase("asc")){
            orderName = new Sort.Order((Sort.Direction.ASC),nameSort);
        }else{
            orderName = new Sort.Order((Sort.Direction.DESC),nameSort);
        }
        return orderName;
    }

    @Override
    public UserEntity findByUserName(String userName) {
        return userRepository.findByUserName(userName);
    }

    @Override
    public List<UserResponse> findByFullNameContaining(String name) {
        try {
            List<UserResponse> userResponses = userRepository.findByFullNameContainsIgnoreCase(name).stream()
                    .map(user -> userMapper.EntityToResponse(user)).collect(Collectors.toList());
            return userResponses;
        } catch (Exception e){
            throw new UserNotFoundException("User not found");
        }
    }

    @Override
    public UserEntity findUserById(long id) {
        Optional<UserEntity> user= userRepository.findById(id);
        if (user.isPresent()){
            return user.get();
        }else {
            throw new UserNotFoundException("User is not found");
        }
    }

    @Override
    public UserResponse findByIdToUserResponse(long id) {
        UserEntity user = findUserById(id);
        return userMapper.EntityToResponse(user);
    }

    /**
     *
     * @param stringList List role được lấy tù form đăng kí.
     *                   Nếu rỗng thỳ add add role măc định là ROLE_USER.
     *
     * @return
     */
    @Override
    public Set<RolesEntity> getListRoleFromRegister(Set<String> stringList) {
        Set<RolesEntity> listRoles = new HashSet<>();
        if (stringList==null){
            RolesEntity userRole = rolesService.findByName(ERoles.ROLE_USER)
                    .orElseThrow(()->new RuntimeException("Error: Role is not found"));
            listRoles.add(userRole);
        }else {
            listRoles = getListRoleFromList(stringList);
        }
        return listRoles;
    }

    @Override
    public boolean existsByUserName(String userName) {

        return userRepository.existsByUserName(userName);
    }

    @Override
    public boolean existsByEmail(String email) {

        return userRepository.existsByEmail(email);
    }

    @Override
    public UserResponse update(long id, UserRequest userRequest) {
        UserEntity userUpdate = findUserById(id);
        userUpdate.setFullName(userRequest.getFullName());
        userUpdate.setEmail(userRequest.getEmail());
        userUpdate.setPhone(userRequest.getPhone());
        userUpdate.setAddress(userRequest.getAddress());
        userUpdate.setUpdated(new Date());
        return userMapper.EntityToResponse(userRepository.save(userUpdate));

    }

    @Override
    public boolean uploadPass(long id, UploadPassRequest passRequest) {
        try {
            UserEntity user = findUserById(id);
            boolean isConfirmPass = confirmPassword(passRequest.getNewPass(), passRequest.getConfirmPass());
            boolean isExistPass = isExistPassword(passRequest.getOldPass(), id);
            if (isConfirmPass && isExistPass){
                user.setPassword(passwordEncoder.encode(passRequest.getNewPass()));
                userRepository.save(user);
                return  true;
            }else {
                throw new PasswordNotMatchException("Password not Match");
            }
        } catch (Exception e){
            throw  new UserNotFoundException("Loi xay ra khi upLoadPassword: "+e.getMessage());
        }

    }

    /**
     * Tạo mới tài khoản
     * @param registerRequest
     * @return
     */
    @Override
    public RegisterResponse saveOrUpdate(RegisterRequest registerRequest) {
        try {
            return mapperRegister.EntityToResponse(userRepository.save(mapperRegister.requestToEntity(registerRequest)));
        } catch (Exception e){
            throw new UserNotFoundException("Loi them moi, cap nhap User: "+ e.getMessage());
        }
    }

    @Override
    public UserResponse addRoleById(long userId, long roleId) {
        UserEntity user= findUserById(userId);
        user.getListRoles().stream().forEach(rolesEntity -> {
            if (rolesEntity.getId()==roleId){
                throw new CustomException("Role is Exist");
            }
        });
        try {
            user.getListRoles().add(rolesService.findById(roleId));
            return userMapper.EntityToResponse(userRepository.save(user));
        } catch (Exception e){
            throw  new CustomException("Role not exist ");
        }

    }

    @Override
    public boolean deleteRole(long userId, long roleId) {
            RolesEntity role = findRoleOfUser(userId,roleId);
            if (role!=null){
                rolesService.deleteRoleByUserIdAndAndRoleId(userId,roleId);
                return true;
            }else {
                throw new CustomException("Role of User not Exist");
            }
    }

    @Override
    public boolean unlockStatus(long userId) {
        UserEntity user =findUserById(userId);
        if (user.isStatus()) {
            user.setStatus(false);
        } else {
            user.setStatus(true);
        }
        userRepository.save(user);
        return true;
    }

    public RolesEntity findRoleOfUser(long userId,long roleId) {
        UserEntity user= findUserById(userId);
       return user.getListRoles().stream()
                .filter(rolesEntity -> roleId==rolesEntity.getId())
                .findAny().orElse(null);
    }

    public boolean confirmPassword(String newPass, String confirmPass){
        if (newPass.equals(confirmPass)){
            return true;
        } else {
            throw new PasswordNotMatchException("Password not Confirm");
        }
    }
    public boolean isExistPassword(String oldPass, long id){
        UserEntity user = findUserById(id);
        if (passwordEncoder.matches(oldPass, user.getPassword())){
            return true;
        } else {
            throw new PasswordNotMatchException("Password not true");
        }
    }

    /**
     *
     * @param stringList List role từ from đăng kí.
     * @return  từ lít role đăng kí, đối chiếu với RoleName Entity và thêm vào lít Role của User
     */
    public Set<RolesEntity> getListRoleFromList(Set<String> stringList){
        Set<RolesEntity> listRoles = new HashSet<>();
        stringList.forEach(role ->{

            switch (role){
                case"admin":
                    RolesEntity adminRole = rolesService.findByName(ERoles.ROLE_ADMIN)
                            .orElseThrow(()-> new RuntimeException("Error: Role is not found"));
                    listRoles.add(adminRole);
                    break;
                case"user":
                    RolesEntity userRole = rolesService.findByName(ERoles.ROLE_USER)
                            .orElseThrow(()-> new RuntimeException("Error: Role is not found"));
                    listRoles.add(userRole);
                    break;
            }
        });
        return listRoles;
    }
}
