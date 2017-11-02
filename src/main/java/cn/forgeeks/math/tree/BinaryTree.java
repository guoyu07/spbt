package cn.forgeeks.math.tree;

import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;


/**
 * Created by zrb on 2017/9/15.
 */
public class BinaryTree {
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static void main(String[] args) {
		int[] array = new int[] { 8, 3, 19, 1, 6, 14, 4, 7 };
		// int[] array = new int[]{8,3,19,1,6};
		// 创建二叉树
		BinaryTreeNode root = createTreeWithValues(array);
		// 中根遍历
		inOrderTraverseTree(root);
		System.out.println();
		// 插入13
		addTreeNode(root, 13);
		// 中根遍历
		inOrderTraverseTree(root);
		System.out.println();
		System.out.println(isAVLBinaryTree(root));
		System.out.println();
		// 删除value=3的节点
		delete(root, 3);
		System.out.println();
		// 中跟遍历结果
		inOrderTraverseTree(root);

	}

	/**
	 * 二叉树的深度
	 */
	public static int depthOfTree(BinaryTreeNode<Integer> root) {
		if (root == null) {
			return 0;
		}
		if (root.leftNode == null && root.rightNode == null) {
			return 1;
		}
		int leftDepth = depthOfTree(root.leftNode);
		int rightDepth = depthOfTree(root.rightNode);
		return Math.max(leftDepth, rightDepth) + 1;
	}

	/**
	 * 二叉树的宽度：各层节点数的最大值
	 * 
	 * @param root
	 *            二叉树的根节点
	 * @return 二叉树的宽度
	 */
	public static int widthOfTree(BinaryTreeNode<Integer> root) {
		if (root == null) {
			return 0;
		}
		// 当前二叉树最大宽度=根节点
		int maxWith = 1;
		int currentWidth = 0;
		// 队列：先进先出，每次循环后保留一层树的某一层的所有节点
		Queue<BinaryTreeNode<Integer>> list = new LinkedList<>();
		list.add(root);
		while (list.size() > 0) {
			currentWidth = list.size();
			// 遍历当前层的所有节点，并将所有节点的子节点加入到队列中
			for (int i = 0; i < currentWidth; i++) {
				BinaryTreeNode<Integer> node = list.peek();
				list.poll();
				if (node.leftNode != null) {
					list.add(node.leftNode);
				}
				if (node.rightNode != null) {
					list.add(node.rightNode);
				}
			}
			maxWith = Math.max(maxWith, list.size());
		}
		return maxWith;
	}

	/**
	 * 二叉树某层中的节点数
	 * 
	 * @param rootNode
	 *            二叉树根节点
	 * @param level
	 *            层
	 * @return level层的节点数
	 */
	public static int numberOfNodesOnLevel(BinaryTreeNode<Integer> rootNode, int level) {
		// 二叉树不存在，或者level不存在的时候节点数为0
		int result = 0;
		if (rootNode == null || level < 1) {
			return result;
		}
		// level=1，为根节点，节点数为1
		if (level == 1) {
			result = 1;
			return result;
		}
		// 递归：node为根节点的二叉树的level层节点数 =
		// node节点左子树（level - 1）层的节点数 + node节点的右子树（level - 1）层的节点数
		return numberOfNodesOnLevel(rootNode.leftNode, level - 1) + numberOfNodesOnLevel(rootNode.rightNode, level - 1);
	}

	/**
	 * 二叉树的叶子节点个数
	 * 
	 * @param rootNode
	 * @return 叶子节点个数
	 */
	public static int numberOfLeafsInTree(BinaryTreeNode<Integer> rootNode) {
		int result = 0;
		if (rootNode == null) {
			return result;
		}
		// rootNode没有子节点，所以根节点为叶节点result=1;
		if (null == rootNode.leftNode && null == rootNode.rightNode) {
			result = 1;
			return result;
		}
		// 递归：root的叶节点 = node的左子树的叶节点 + node的右子树的叶节点
		return numberOfLeafsInTree(rootNode.leftNode) + numberOfLeafsInTree(rootNode.rightNode);
	}

	/**
	 * 二叉树的最大距离（直径）
	 * 
	 * @param rootNode
	 *            根节点
	 * @return 最大距离
	 */
	public static int maxDistanceOfTree(BinaryTreeNode<Integer> rootNode) {
		if (rootNode == null) {
			return 0;
		}
		// 1、最远距离经过根节点：距离 = 左子树深度 + 右子树深度
		int distance = depthOfTree(rootNode.leftNode) + depthOfTree(rootNode.rightNode);
		// 2、最远距离在根节点左子树上，即计算左子树最远距离
		int disLeft = maxDistanceOfTree(rootNode.leftNode);
		// 3、最远距离在根节点右子树上，即计算右子树最远距离
		int disRight = maxDistanceOfTree(rootNode.rightNode);
		return Math.max(distance, Math.max(disLeft, disRight));
	}

	public static int maxDistanceOfTree2(BinaryTreeNode<Integer> rootNode) {
		if (rootNode == null) {
			return 0;
		}
		return propertyOfTreeNode(rootNode).distance;
	}

	public static TreeNodeProperty propertyOfTreeNode(BinaryTreeNode<Integer> rootNode) {
		if (rootNode == null) {
			return new TreeNodeProperty();
		}
		TreeNodeProperty left = propertyOfTreeNode(rootNode.leftNode);
		TreeNodeProperty right = propertyOfTreeNode(rootNode.rightNode);
		TreeNodeProperty p = new TreeNodeProperty();
		// 当前节点的树的深度depth = 左子树深度 + 右子树深度 + 1；（根节点也占一个depth）
		p.depth = Math.max(left.depth, right.depth) + 1;
		p.distance = Math.max(Math.max(left.distance, right.distance), left.depth + right.depth);
		return p;
	}

	/**
	 * 二叉树中某个节点到根节点的路径
	 * 
	 * @param rootNode
	 *            根节点
	 * @param treeNode
	 *            节点
	 * @return 路径队列
	 */
	public static Stack<BinaryTreeNode> pathOfTreeNode(BinaryTreeNode<Integer> rootNode, int treeNode) {
		Stack<BinaryTreeNode> pathList = new Stack<>();
		isFoundTreeNode(rootNode, treeNode, pathList);
		return pathList;
	}

	/**
	 * 查找某个节点是否在树中
	 * 
	 * @param rootNode
	 *            根节点
	 * @param treeNode
	 *            待查找的节点
	 * @param path
	 *            根节点到待查找节点的路径
	 * @return 是否找到该节点
	 */
	public static boolean isFoundTreeNode(BinaryTreeNode<Integer> rootNode, int treeNode, Stack<BinaryTreeNode> path) {
		if (rootNode == null) {
			return false;
		}
		// 当前节点就是需要找的节点
		if (rootNode.value == treeNode) {
			path.add(rootNode);
			return true;
		}
		// 将路过的节点压入栈中
		path.add(rootNode);
		// 先在左子树中查找
		boolean find = isFoundTreeNode(rootNode.leftNode, treeNode, path);
		if (!find) {
			// 如果没有找到，然后在右子树中查找
			find = isFoundTreeNode(rootNode.rightNode, treeNode, path);
		}
		if (!find) {
			path.pop();
		}
		return find;
	}

	/**
	 * 创建二叉树
	 */
	public static BinaryTreeNode<Integer> createTreeWithValues(int[] values) {
		BinaryTreeNode root = null;
		for (int value : values) {
			root = addTreeNode(root, value);
		}
		return root;
	}

	/**
	 * 在treeNode中添加值为value的节点
	 */
	public static BinaryTreeNode<Integer> addTreeNode(BinaryTreeNode<Integer> treeNode, int value) {
		if (treeNode == null) {
			treeNode = new BinaryTreeNode<>();
			treeNode.value = value;
		} else {
			if (value <= treeNode.value) {
				treeNode.leftNode = addTreeNode(treeNode.leftNode, value);
			} else {
				treeNode.rightNode = addTreeNode(treeNode.rightNode, value);
			}
		}
		return treeNode;
	}

	/**
	 * 中根遍历
	 */
	public static void inOrderTraverseTree(BinaryTreeNode<Integer> rootNode) {
		if (rootNode != null) {
			inOrderTraverseTree(rootNode.leftNode);
			System.out.print(" " + rootNode.value + " ");
			inOrderTraverseTree(rootNode.rightNode);
		}
	}

	/**
	 * 二叉树查找
	 */
	public static BinaryTreeNode<Integer> search(BinaryTreeNode<Integer> rootNode, int value) {
		if (rootNode != null) {
			if (rootNode.value == value) {
				return rootNode;
			}
			if (value > rootNode.value) {
				return search(rootNode.rightNode, value);
			} else {
				return search(rootNode.leftNode, value);
			}
		}
		return rootNode;
	}

	/**
	 * 寻找value节点的父节点
	 */
	public static BinaryTreeNode<Integer> searchParent(BinaryTreeNode<Integer> rootNode, int value) {
		// 如果当前节点为null，或者当前节点为根节点。返回null
		if (rootNode == null || rootNode.value == value) {
			return null;
		} else {
			// 当前节点的左儿子或者右儿子等于value，则返回当前节点。
			if (rootNode.leftNode != null && value == (Integer) rootNode.leftNode.value
					|| rootNode.rightNode != null && value == (Integer) rootNode.rightNode.value) {
				return rootNode;
			}
			// 判断需要寻找的节点的位置，
			if (value > rootNode.value && rootNode.rightNode != null) {
				return searchParent(rootNode.rightNode, value);
			} else {
				return searchParent(rootNode.leftNode, value);
			}
		}
	}

	/**
	 * 删除rootNode为根节点的二叉树中值为value的节点
	 */
	public static BinaryTreeNode<Integer> delete(BinaryTreeNode<Integer> rootNode, int value) {
		// 判断是否删除的节点为根节点
		if (rootNode == null && rootNode.value == value) {
			rootNode = null;
			return rootNode;
		}
		// 找到删除的节点的父节点
		BinaryTreeNode<Integer> parentNode = searchParent(rootNode, value);
		// 找不到父节点，表示该二叉树没有对应的节点
		if (parentNode == null) {
			return rootNode;
		}
		BinaryTreeNode<Integer> deleteNode = search(rootNode, value);
		// 找不到该节点
		if (deleteNode == null) {
			return rootNode;
		}
		// 需要删除的节点，为叶子节点
		if (deleteNode.leftNode == null && deleteNode.rightNode == null) {
			deleteNode = null;
			if (parentNode.leftNode != null && value == (Integer) parentNode.leftNode.value) {
				parentNode.leftNode = null;
			} else {
				parentNode.rightNode = null;
			}
		}
		// 需要删除的节点，只有左子树，左子树继承该删除的位置
		else if (deleteNode.rightNode == null) {
			if (parentNode.leftNode != null && value == (Integer) parentNode.leftNode.value) {
				parentNode.leftNode = deleteNode.leftNode;
			} else {
				parentNode.rightNode = deleteNode.leftNode;
			}
		}
		// 需要删除的节点，只有右子树，右子树继承该删除的位置
		else if (deleteNode.leftNode == null) {
			if (parentNode.leftNode != null && value == (Integer) parentNode.leftNode.value) {
				parentNode.leftNode = deleteNode.rightNode;
			} else {
				parentNode.rightNode = deleteNode.rightNode;
			}
		}
		// 要删除的节点既有左海子，又有右孩子。需要选择一个设施的节点继承，我们选择左子树中的最右节点
		else {
			BinaryTreeNode<Integer> tmpDeleteNode = deleteNode;
			BinaryTreeNode<Integer> selectNode = tmpDeleteNode.leftNode;
			if (selectNode.rightNode == null) {
				selectNode.rightNode = deleteNode.rightNode;
			} else {
				// 找到deleteNode的左子树中的最右节点，即最大节点
				while (selectNode.rightNode != null) {
					tmpDeleteNode = selectNode;
					selectNode = selectNode.rightNode;
				}
				// 将选出的继承节点的左子树赋值给父节点的右子树
				tmpDeleteNode.rightNode = selectNode.leftNode;
				// 继承节点继承需要删除的左右子树
				selectNode.leftNode = deleteNode.leftNode;
				selectNode.rightNode = deleteNode.rightNode;
			}
			// 将选出的继承节点进行继承（删除对应节点）
			if (parentNode.leftNode != null && value == (Integer) parentNode.leftNode.value) {
				parentNode.leftNode = selectNode;
			} else {
				parentNode.rightNode = selectNode;
			}
		}
		return rootNode;
	}

	/**
	 * 二叉树种两个节点的最近公共节点
	 * 
	 * @param rootNode
	 *            根节点
	 * @param nodeA
	 *            第一个节点
	 * @param nodeB
	 *            第二个节点
	 * @return 最近的公共节点
	 */
	public static int parentOfNode(BinaryTreeNode<Integer> rootNode, int nodeA, int nodeB) {
		if (rootNode == null) {
			return -1;
		}
		// 两个节点是同一个节点
		if (nodeA == nodeB) {
			return nodeA;
		}
		// 其中一个点为根节点
		if (rootNode.value == nodeA || rootNode.value == nodeB) {
			return rootNode.value;
		}
		// 从根节点到节点A的路径
		Stack<BinaryTreeNode> pathA = pathOfTreeNode(rootNode, nodeA);
		// 从根节点到节点B的路径
		Stack<BinaryTreeNode> pathB = pathOfTreeNode(rootNode, nodeB);
		// 寻找的节点不在树中
		if (pathA.size() == 0 || pathB.size() == 0) {
			return -1;
		}
		// 将路径的数据结构，变为数组
		int[] arrayA = new int[pathA.size()];
		int[] arrayB = new int[pathB.size()];
		for (int i = pathA.size() - 1; i >= 0; i--) {
			arrayA[i] = (int) pathA.pop().value;
		}
		for (int i = pathB.size() - 1; i >= 0; i--) {
			arrayB[i] = (int) pathB.pop().value;
		}
		// 第i+1个不相同的节点出现，则第i个节点为最近公共节点
		for (int i = 0; i < arrayA.length - 1 && i < arrayB.length - 1; i++) {
			if (arrayA[i + 1] != arrayB[i + 1]) {
				return arrayA[i];
			}
			if (i + 1 == arrayA.length - 1) {
				return arrayA[arrayA.length - 1];
			}
			if (i + 1 == arrayB.length - 1) {
				return arrayB[arrayB.length - 1];

			}
		}
		return -1;
	}

	/**
	 * 二叉树中两个节点之间的路径
	 * 
	 * @param rootNode
	 *            根节点
	 * @param nodeA
	 *            第一个节点
	 * @param nodeB
	 *            第二个节点
	 * @return 路径
	 */
	/*public static List<Integer> pathFromNode(BinaryTreeNode<Integer> rootNode, int nodeA, int nodeB) {
		if (rootNode == null) {
			return null;
		}
		List<Integer> result = new ArrayList<>();
		if (nodeA == nodeB) {
			result.add(nodeA);
			result.add(nodeB);
			return result;
		}
		// 从根节点到节点A的路径
		Stack<BinaryTreeNode> pathA = pathOfTreeNode(rootNode, nodeA);
		// 从根节点到节点B的路径
		Stack<BinaryTreeNode> pathB = pathOfTreeNode(rootNode, nodeB);
		if (rootNode.value == nodeB) {
			pathA.forEach(new Consumer<BinaryTreeNode>() {
				@Override
				public void accept(BinaryTreeNode binaryTreeNode) {
					result.add((Integer) binaryTreeNode.value);
				}
			});
			return result;
		}
		if (rootNode.value == nodeA) {
			pathB.forEach(new Consumer<BinaryTreeNode>() {
				@Override
				public void accept(BinaryTreeNode binaryTreeNode) {
					result.add((Integer) binaryTreeNode.value);
				}
			});
			return result;
		}
		// 寻找的节点不在树中
		if (pathA.size() == 0 || pathB.size() == 0) {
			return null;
		}
		// 将路径的数据结构，变为数组
		int[] arrayA = new int[pathA.size()];
		int[] arrayB = new int[pathB.size()];
		for (int i = pathA.size() - 1; i >= 0; i--) {
			arrayA[i] = (int) pathA.pop().value;
		}
		for (int i = pathB.size() - 1; i >= 0; i--) {
			arrayB[i] = (int) pathB.pop().value;
		}
		// 第i+1个不相同的节点出现，则第i个节点为最近公共节点
		int lastNode = -1;
		for (int i = 0; i < arrayA.length - 1 && i < arrayB.length - 1; i++) {
			if (arrayA[i + 1] != arrayB[i + 1]) {
				lastNode = i;
				break;
			}
			if (i + 1 == arrayA.length - 1) {
				lastNode = arrayA.length - 1;
				break;
			}
			if (i + 1 == arrayB.length - 1) {
				lastNode = arrayB.length - 1;
				break;
			}
		}
		for (int i = arrayA.length - 1; i >= lastNode; i--) {
			result.add(arrayA[i]);
		}
		for (int i = lastNode + 1; i < arrayB.length; i++) {
			result.add(arrayB[i]);
		}
		return result;
	}
*/
	
	/**
	 * 翻转二叉树
	 * 
	 * @param rootNode
	 *            根节点
	 * @return 翻转后的二叉树
	 */
	public static BinaryTreeNode invertBinaryTree(BinaryTreeNode<Integer> rootNode) {
		if (rootNode == null) {
			return null;
		}
		// 只有一个根节点
		if (rootNode.leftNode == null && rootNode.rightNode == null) {
			return rootNode;
		}
		invertBinaryTree(rootNode.leftNode);
		invertBinaryTree(rootNode.rightNode);
		BinaryTreeNode tempNode = rootNode.leftNode;
		rootNode.leftNode = rootNode.rightNode;
		rootNode.rightNode = tempNode;
		return rootNode;
	}

	/**
	 * 是否完全二叉树 完全二叉树：若设二叉树的高度为h，除第h层外，其它各层的结点数都达到最大个数，第h层有叶子结点，并且叶子结点都是从左到右依次排布
	 * 
	 * @param rootNode
	 *            根节点
	 * @return 是否是完全二叉树
	 */
	public static boolean isCompleteBinaryTree(BinaryTreeNode<Integer> rootNode) {
		if (rootNode == null) {
			return false;
		}
		// 左子树和右子树都是空，则是完全二叉树
		if (rootNode.leftNode == null && rootNode.rightNode == null) {
			return true;
		}
		// 左子树是空，右子树不是空，则不是完全二叉树
		if (rootNode.leftNode == null && rootNode.rightNode != null) {
			return false;
		}
		Deque<BinaryTreeNode<Integer>> queue = new LinkedList<>();
		queue.add(rootNode);
		// 是否满足二叉树
		boolean isComplete = false;
		while (queue.size() > 0) {
			BinaryTreeNode<Integer> node = queue.pop();
			// 左子树为空且右子树不为空，则不是完全二叉树
			if (node.leftNode == null && node.rightNode != null) {
				return false;
			}
			// 前面的节点已满足完全二叉树,如果还有孩子节点，则不是完全二叉树
			if (isComplete && (node.leftNode != null || node.rightNode != null)) {
				return false;
			}
			// 右子树为空，则已经满足完全二叉树
			if (node.rightNode == null) {
				isComplete = true;
			}
			// 将子节点压入
			if (node.leftNode != null) {
				queue.add(node.leftNode);
			}
			if (node.rightNode != null) {
				queue.add(node.rightNode);
			}
		}
		return isComplete;
	}

	/**
	 * 是否满二叉树 满二叉树：除了叶结点外每一个结点都有左右子叶且叶子结点都处在最底层的二叉树
	 * 
	 * @param rootNode
	 *            根节点
	 * @return 是否满二叉树
	 */
	public static boolean isFullBinaryTree(BinaryTreeNode<Integer> rootNode) {
		if (rootNode == null) {
			return false;
		}
		int depth = depthOfTree(rootNode);
		int leafNum = numberOfLeafsInTree(rootNode);
		if (leafNum == Math.pow(2, (depth - 1))) {
			return true;
		}
		return false;
	}

	/**
	 * 是否平衡二叉树
	 * 
	 * @param rootNode
	 *            根节点
	 * @return 是否平衡二叉树
	 */
	static int height;

	public static boolean isAVLBinaryTree(BinaryTreeNode<Integer> rootNode) {
		if (rootNode == null) {
			height = 0;
			return true;
		}
		if (rootNode.leftNode == null && rootNode.rightNode == null) {
			height = 1;
			return true;
		}
		boolean isAVLLeft = isAVLBinaryTree(rootNode.leftNode);
		int heightLeft = height;
		boolean isAVLRight = isAVLBinaryTree(rootNode.rightNode);
		int heightRight = height;
		height = Math.max(heightLeft, heightRight) + 1;
		return isAVLLeft && isAVLRight && Math.abs(heightLeft - heightRight) <= 1;
	}
}

/**
 * 二叉树节点
 */
class BinaryTreeNode<T> {
	T value;
	BinaryTreeNode leftNode;
	BinaryTreeNode rightNode;
}

class TreeNodeProperty {
	int depth = 0;
	int distance = 0;
}