根据每日 气温 列表，请重新生成一个列表，对应位置的输出是需要再等待多久温度才会升高超过该日的天数。如果之后都不会升高，请在该位置用 0 来代替。

例如，给定一个列表 temperatures = [73, 74, 75, 71, 69, 72, 76, 73]，你的输出应该是 [1, 1, 4, 2, 1, 1, 0, 0]。

提示：气温 列表长度的范围是 [1, 30000]。每个气温的值的均为华氏度，都是在 [30, 100] 范围内的整数。

来源：力扣（LeetCode）
链接：https://leetcode-cn.com/problems/daily-temperatures
著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
class Solution {
    public int[] dailyTemperatures(int[] T) {
        int n=T.length;
        int[] res=new int[n];
        Stack<Integer> stack=new Stack<>();
        for(int i=0;i<n;i++){
            while(!stack.isEmpty()&&T[stack.peek()]<T[i]){ 
                int index=stack.pop();
                res[index]=i-index;
            }
            stack.push(i);
        }
        return res;
    }
}

给定一个循环数组（最后一个元素的下一个元素是数组的第一个元素），输出每个元素的下一个更大元素。数字 x 的下一个更大的元素是按数组遍历顺序，这个数字之后的第一个比它更大的数，这意味着你应该循环地搜索它的下一个更大的数。如果不存在，则输出 -1。

来源：力扣（LeetCode）
链接：https://leetcode-cn.com/problems/next-greater-element-ii
著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
class Solution {
    public int[] nextGreaterElements(int[] nums) {
        int n=nums.length;
        int[] res=new int[n];
        Arrays.fill(res,-1);
        Stack<Integer> stack=new Stack<>();
        for(int i=0;i<2*n;i++){
            int j=i%n;
            while(!stack.isEmpty()&&nums[stack.peek()%n]<nums[j]){
                int index=stack.pop()%n;
                res[index]=nums[j];
                
            }
            if(i<n) stack.push(i);
        }
        return res;
    }
}

给定一个数组 nums，有一个大小为 k 的滑动窗口从数组的最左侧移动到数组的最右侧。你只可以看到在滑动窗口内的 k 个数字。滑动窗口每次只向右移动一位。

返回滑动窗口中的最大值。

来源：力扣（LeetCode）
链接：https://leetcode-cn.com/problems/sliding-window-maximum
著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
class Solution {
    public int[] maxSlidingWindow(int[] nums, int k) {
        Deque<Integer> queue=new LinkedList<>();
        int n=nums.length-k+1;
        int[] res=new int[n];
        int j=0;
        for(int i=0;i<nums.length;i++){
            while(!queue.isEmpty()&&nums[queue.peekLast()]<=nums[i]){
                queue.pollLast();
            }
            queue.offerLast(i);
            if(queue.peekFirst()<=i-k){
                queue.pollFirst();
            }
            if(i>=k-1){
                res[j++]=nums[queue.peekFirst()];
            }
        }
        return res;
    }
}

