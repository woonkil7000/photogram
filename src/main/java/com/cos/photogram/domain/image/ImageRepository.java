package com.cos.photogram.domain.image;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ImageRepository extends JpaRepository<Image, Integer>{

	@Query(value = "select * from image where userId in (select toUserId from subscribe where fromUserId = :principalId) order by id desc", nativeQuery = true)
	Page<Image> mFeed(int principalId, Pageable pageable);

	@Query(value = "select * from image where userId in (select toUserId from subscribe where fromUserId = :principalId) order by id desc", nativeQuery = true)
	Page<Image> mStory(int principalId, Pageable pageable);

	@Query(value = "SELECT i.* FROM image i INNER JOIN (SELECT imageId, COUNT(imageId) likeCount FROM likes GROUP BY imageId " +
			" ORDER BY likeCount DESC) c ON i.id=c.imageId ORDER BY likeCount desc", nativeQuery = true)
	List<Image> mExplore(int principalId);

	//@Query(value = "select * from image where id in (select imageId from (select imageId, count(imageId) likeCount from likes group by imageId order by 2 desc) t) and userId != :principalId  ", nativeQuery = true)
	//List<Image> mExplore(int principalId);
}
