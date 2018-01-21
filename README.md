## Debugging hazelcast list issue with model updates

### What?
We ran into a stupid issue with hazelcast distributed lists where the list seemed to behave incorrectly.

### Why?
We ran a cluster with multiple nodes (obviously?) that was used to store some objects which were then processed one at a time.
Everything worked fine except sometimes during the rollout of a new feature when the objects won't be removed from the list forever.

### So, why?
Our rollouts happen one node at a time. Imagine there are some instances of User in the list. In the new release, we add some new attributes to user so that `hashcode` of the existing user object changes.
That's it. Now when a node with new code tries to lookup the user it won't find because `equals` wouldn't work now. So this node cannot remove users from the list. This problem is spread to other nodes slowly as the deployment progresses till all nodes are infected. Now the list is a deadlist - it can't be emptied and so no new objects can be put into it. So, the processing stops.

### Proof
This app has a group (distributed list) which stores members. Users join or leave the group randomly. We first run the app in `join-only` mode so that we have some users to play with.
```
java -cp ~/path/to/hazelcast-all-3.5.2.jar:. com.abhishekjain.hazelcast.Main join-only
```
It will start listing when users add the group.

Now change the user model to remove #isPersonAwesome field. Compile and run without the join-only arg now.
```
java -cp ~/path/to/hazelcast-all-3.5.2.jar:. com.abhishekjain.hazelcast.Main
```

It will try to remove users from the list randomly but fail.
> current size before leaving: 10
> Unable to remove user: #{abhishek}
> Members present:
> #{akshat}
> #{akansha}
> #{tanvi}
> #{utsav}
> #{charu}
> #{nonu}
> #{nikku}
> #{ashish}
> #{abhishek}
> #{rahul}
> 10 -- 23:24:05-540
> 
> current size before leaving: 10
> Unable to remove user: #{akansha}
> Members present:
> #{akshat}
> #{akansha}
> #{tanvi}
> #{utsav}
> #{charu}
> #{nonu}
> #{nikku}
> #{ashish}
> #{abhishek}
> #{rahul}
> 10 -- 23:24:07-542
