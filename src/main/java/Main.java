import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.common.base.Optional;
import com.google.common.base.Strings;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Iterators;
import com.google.common.collect.Lists;
import com.pheu.core.casara.CassandraSessionFactory;
import com.pheu.core.casara.PagingResult;
import com.pheu.twis.command.impl.RelationshipCommandImpl;
import com.pheu.twis.command.impl.TimelineCommandImpl;
import com.pheu.twis.command.impl.TweetCommandImpl;
import com.pheu.twis.command.impl.UserCommandImpl;
import com.pheu.twis.command.impl.UserlineCommandImpl;
import com.pheu.twis.dao.RelationshipDao;
import com.pheu.twis.dao.TimelineDao;
import com.pheu.twis.dao.TweetDao;
import com.pheu.twis.dao.UserDao;
import com.pheu.twis.dao.UserlineDao;
import com.pheu.twis.dao.impl.RelationshipDaoImpl;
import com.pheu.twis.dao.impl.TimelineDaoImpl;
import com.pheu.twis.dao.impl.TweetDaoImpl;
import com.pheu.twis.dao.impl.UserDaoImpl;
import com.pheu.twis.dao.impl.UserlineDaoImpl;
import com.pheu.twis.domain.Follower;
import com.pheu.twis.domain.Friend;
import com.pheu.twis.domain.TimeLine;
import com.pheu.twis.domain.Tweet;
import com.pheu.twis.domain.User;
import com.pheu.twis.domain.UserLine;

public class Main {

	public static void main(String[] args) {
		CassandraSessionFactory sessionFactory = new CassandraSessionFactory();
		sessionFactory.setKeyspace("twis");
		List<String> nodes = new ArrayList<String>();
		nodes.add("localhost");
		sessionFactory.setNodes(nodes);

		UserDao userDao = new UserDaoImpl(new UserCommandImpl(sessionFactory));
		System.out.println(userDao.signup("quy2", "pass2"));
		System.out.println(userDao.signup("user1", "pass2"));
		System.out.println(userDao.signup("quy1", "pass1"));
		System.out.println(userDao.signup("quy3", "pass3"));
		System.out.println(userDao.signup("quy4", "pass4"));
		System.out.println(userDao.signup("quy5", "pass5"));
		System.out.println(userDao.signup("quy6", "pass6"));
		System.out.println(userDao.signup("quy7", "pass7"));
		System.out.println(userDao.signup("quy8", "pass8"));

		TweetDao tweetDao = new TweetDaoImpl(new TweetCommandImpl(sessionFactory));

		/*
		 * int n = 10; Map<String, Date> dates = new HashMap<String, Date>();
		 * for (int i = 1; i <= n; i++) { String id = String.valueOf(i);
		 * 
		 * Tweet tweet = new Tweet(); tweet.setTweetId(id);
		 * tweet.setUsername("quy" + i); tweet.setBody("This is a tweet! - " +
		 * i); try { Thread.sleep(500); } catch (InterruptedException e) { //
		 * TODO Auto-generated catch block e.printStackTrace(); } Date now = new
		 * Date(System.currentTimeMillis()); tweet.setSince(now);
		 * 
		 * System.out.println(tweetDao.post(tweet) + tweet.toString());
		 * dates.put(id, now); }
		 * 
		 * Optional<Tweet> tweetOptional = tweetDao.get("1"); if
		 * (tweetOptional.isPresent()) {
		 * System.out.println(tweetOptional.get().toString()); } tweetOptional =
		 * tweetDao.get("2"); if (tweetOptional.isPresent()) {
		 * System.out.println(tweetOptional.get().toString()); }
		 * 
		 * Optional<PagingResult<Tweet>> rsPaging = tweetDao.list(null, 2);
		 * while(true) { if(!rsPaging.isPresent() ||
		 * Strings.isNullOrEmpty(rsPaging.get().getNextPage())) { break; }
		 * System.out.println(rsPaging.get().getIds()); rsPaging =
		 * tweetDao.list(rsPaging.get().getNextPage(), 2); }
		 */

		Optional<PagingResult<Tweet>> rsPaging = tweetDao.list(null, 2);
		while (true) {
			if (!rsPaging.isPresent() || Strings.isNullOrEmpty(rsPaging.get().getNextPage())) {
				break;
			}
			System.out.println(rsPaging.get().getIds());
			rsPaging = tweetDao.list(rsPaging.get().getNextPage(), 2);
		}

		RelationshipDao relationshipDao = new RelationshipDaoImpl(new RelationshipCommandImpl(sessionFactory));
		System.out.println(relationshipDao.follow("quy1", "quy2", new Date()));
		System.out.println(relationshipDao.follow("quy1", "quy3", new Date()));
		System.out.println(relationshipDao.follow("quy1", "quy4", new Date()));
		System.out.println(relationshipDao.follow("quy1", "quy5", new Date()));
		System.out.println(relationshipDao.follow("quy1", "quy6", new Date()));
		System.out.println(relationshipDao.follow("quy1", "quy7", new Date()));
		System.out.println(relationshipDao.follow("quy1", "quy8", new Date()));
		System.out.println(relationshipDao.follow("quy3", "quy2", new Date()));
		System.out.println(relationshipDao.follow("quy4", "quy2", new Date()));
		System.out.println(relationshipDao.follow("quy5", "quy2", new Date()));
		System.out.println(relationshipDao.follow("user1", "quy1", new Date()));
		System.out.println(relationshipDao.follow("quy1", "user1", new Date()));

		Optional<PagingResult<Friend>> friends = relationshipDao.friends(null, "quy1", 2);
		while (true) {
			System.out.println(friends.get().getIds());
			if (!friends.isPresent() || Strings.isNullOrEmpty(friends.get().getNextPage())) {
				break;
			}
			friends = relationshipDao.friends(friends.get().getNextPage(), "quy1", 2);
		}

		Optional<PagingResult<Follower>> followers = relationshipDao.followers(null, "user1", 2);
		System.out.println(followers.get().getIds());

		/*
		 * TimelineDao timelineDao = new TimelineDaoImpl(new
		 * TimelineCommandImpl(sessionFactory));
		 * timelineDao.batchCreate(ImmutableList.of( new TimeLine("user3", new
		 * Date(System.currentTimeMillis()), String.valueOf(1)), new
		 * TimeLine("user3", new Date(System.currentTimeMillis() + 1),
		 * String.valueOf(2)), new TimeLine("user3", new
		 * Date(System.currentTimeMillis() + 2), String.valueOf(3)), new
		 * TimeLine("user3", new Date(System.currentTimeMillis() + 3),
		 * String.valueOf(4)), new TimeLine("user3", new
		 * Date(System.currentTimeMillis() + 4), String.valueOf(5)), new
		 * TimeLine("user3", new Date(System.currentTimeMillis() + 5),
		 * String.valueOf(6)) ));
		 * 
		 * Optional<PagingResult<TimeLine>> timelines =
		 * timelineDao.listPaging(null, "user3", 2); while (true) {
		 * System.out.println(timelines.get().getIds().toString()); if
		 * (!timelines.isPresent() ||
		 * Strings.isNullOrEmpty(timelines.get().getNextPage())) { break; }
		 * timelines = timelineDao.listPaging(timelines.get().getNextPage(),
		 * "user3", 2); }
		 */

		UserlineDao userlineDao = new UserlineDaoImpl(new UserlineCommandImpl(sessionFactory));
		userlineDao.batchCreate(ImmutableList.of(
		 new UserLine("user1", new Date(System.currentTimeMillis()), "11")));
		// String.valueOf(1)),
		// new UserLine("user1", new Date(System.currentTimeMillis() + 1),
		// String.valueOf(2)),
		// new UserLine("user1", new Date(System.currentTimeMillis() + 2),
		// String.valueOf(3)),
		// new UserLine("user1", new Date(System.currentTimeMillis() + 3),
		// String.valueOf(4)),
		// new UserLine("user1", new Date(System.currentTimeMillis() + 4),
		// String.valueOf(5)),
		// new UserLine("user1", new Date(System.currentTimeMillis() + 5),
		// String.valueOf(6))
		// ));
		Optional<PagingResult<UserLine>> userlines = userlineDao.listPaging(null, "user1", 2);
		while (true) {
			System.out.println(userlines.get().getIds().toString());
			if (!userlines.isPresent() || Strings.isNullOrEmpty(userlines.get().getNextPage())) {
				break;
			}
			userlines = userlineDao.listPaging(userlines.get().getNextPage(), "user1", 2);
		}

		sessionFactory.close();
		System.exit(0);
	}

}
