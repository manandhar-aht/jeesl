package org.jeesl.util.comparator.primitive;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Ranking
{
	public Ranking()
	{
		
	}
	
	public int[] rank(int[] points)
	{
		List<Integer> tmpPoints = new ArrayList<Integer>();
		for(int i : points){tmpPoints.add(i);}
		List<Integer> tmp = rank(tmpPoints);
		
		int[] result = new int[tmp.size()];
		for(int i=0;i<tmp.size();i++){result[i]=tmp.get(i);}
		return result;
	}
	
	public List<Integer> rank(List<Integer> listPoints)
	{
		List <Rank> rankList = new ArrayList<Rank>();
        List <Integer> tmp = new ArrayList<Integer>();
		Rank[] sortList = new Rank[listPoints.size()];
		List <Integer> result = new ArrayList<Integer>();

        tmp.addAll(listPoints);
		Collections.sort(listPoints);
        Collections.reverse(listPoints);
		int zaehler = 1;
		
		for (Integer i: listPoints)
		{
            rankList.add(new Rank(zaehler, i));
            rankList = getEqualPoints(rankList);
            zaehler++;
        }

        for(Rank r : rankList)
        {
            for (int i = 0; i < tmp.size(); i++) {
                if (r.getScore() == tmp.get(i) && sortList[i] == null) { sortList[i] = r; }
            }
        }
        for(Rank ra : sortList) {
            result.add(ra.getIndex());
        }
        return result;
	}

    List<Rank> getEqualPoints (List<Rank> rankList)
	{
        for(int j = 0; j < rankList.size(); j++)
        {
            for (int k = 1+j; k < rankList.size(); k++)
            {
                setRank(rankList.get(j), rankList.get(k));
            }
        }
        return rankList;
    }

    public void setRank(Rank rank1, Rank rank2)
    {
        if (rank1.getScore() == rank2.getScore())
            rank2.setIndex(rank1.getIndex());
    }

	public class Rank implements Comparator<Rank>
	{
		public int index, score;

		public	Rank () {}
		public	Rank (int index, int score)
		{
			this.index = index;
			this.score = score;
		}

		public int getIndex() {return index;}
		public void setIndex(int index) {this.index = index;}

		public int getScore() {return score;}
		public void setScore(int score) {this.score = score;}
		@Override
		public int compare(Rank a, Rank b)
		{
			return(new Integer(a.getScore())).compareTo(b.getScore())*-1;
		}
	}
}
