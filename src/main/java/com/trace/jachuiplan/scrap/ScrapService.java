/// 이화경
package com.trace.jachuiplan.scrap;

import com.trace.jachuiplan.DataNotFoundException;
import com.trace.jachuiplan.regioncd.Regioncd;
import com.trace.jachuiplan.regioncd.RegioncdRepository;
import com.trace.jachuiplan.user.UserService;
import com.trace.jachuiplan.user.Users;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class ScrapService {

    private final UserService userService;
    private final ScrapRepository scrapRepository;
    private final RegioncdRepository regioncdRepository;

    @Transactional
    public Boolean addScrap(Long regioncdId, String username){
        if(getScrap(regioncdId, username) != null){
            return false;
        }
        Optional<Users> users = userService.findByUsername(username);
        Optional<Regioncd> regioncd = regioncdRepository.findById(regioncdId);
        Scrap scrap = new Scrap();
        scrap.setRegioncd(regioncd.get());
        scrap.setUsers(users.get());

        scrapRepository.save(scrap);
        return true;
    }

    public Boolean removeScrap(Long regioncdId, String username){
        Optional<Users> users = userService.findByUsername(username);
        if(users.isEmpty()){
            throw new DataNotFoundException("유저 정보가 존재하지 않습니다.");
        }
        Optional<Scrap> scrap = scrapRepository.findByRegioncdIdAndUsersUno(regioncdId, users.get().getUno());
        if(scrap.isEmpty()){
            throw new DataNotFoundException("스크랩 정보가 존재하지 않습니다.");
        }
        scrapRepository.delete(scrap.get());
        return true;
    }

    @Transactional
    public Scrap getScrap(Long regioncdId, String username){
        Optional<Users> users = userService.findByUsername(username);
        if(users.isEmpty()){
            throw new DataNotFoundException("유저 정보가 존재하지 않습니다.");
        }
        Optional<Scrap> scrap = scrapRepository.findByRegioncdIdAndUsersUno(regioncdId, users.get().getUno());
        if(scrap.isEmpty()){
            return null;
        }
        return scrap.get();
    }

    // 스크랩한 지역
    public List<ScrapedListDTO> findScrapedBuilding(String startYearMonth, String endYearMonth,
                                                    String rentType, Double minArea, Double maxArea, Integer minBuildYear,
                                                    Integer maxBuildYear, Integer minFloor, Integer maxFloor, Long uno) {
        return scrapRepository.findScrapedBuilding(startYearMonth, endYearMonth, rentType, minArea, maxArea, minBuildYear, maxBuildYear, minFloor, maxFloor, uno);
    }

    // 스크랩한 지역
    public List<ScrapedListDTO> findScrapedOfficeHotel(String startYearMonth, String endYearMonth,
                                                              String rentType, Double minArea, Double maxArea, Integer minBuildYear,
                                                              Integer maxBuildYear, Integer minFloor, Integer maxFloor, Long uno) {
        return scrapRepository.findScrapedOfficeHotel(startYearMonth, endYearMonth, rentType, minArea, maxArea, minBuildYear, maxBuildYear, minFloor, maxFloor, uno);
    }


}
