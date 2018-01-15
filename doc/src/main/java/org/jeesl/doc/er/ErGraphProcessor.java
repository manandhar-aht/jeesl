package org.jeesl.doc.er;

import java.io.File;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.MappedSuperclass;

import net.sf.ahtutils.model.qualifier.EjbErNode;
import net.sf.exlp.util.io.ClassUtil;
import net.sf.exlp.util.io.dir.RecursiveFileFinder;

import org.apache.commons.io.filefilter.FileFilterUtils;
import org.jeesl.util.ReflectionUtil;
import org.metachart.xml.graph.Edge;
import org.metachart.xml.graph.Edges;
import org.metachart.xml.graph.Graph;
import org.metachart.xml.graph.Node;
import org.metachart.xml.graph.Nodes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ErGraphProcessor
{
	private static enum Cardinality {OneToOne,OneToMany,ManyToOne,ManyToMany}
	final static Logger logger = LoggerFactory.getLogger(ErGraphProcessor.class);
	
	private File fBase;
	
	private Map<String,Node> mapNodes;
	private Map<String,Edge> mapEdges;
	private Map<String,Boolean> mapChilds;
	private Graph graph;
	
	public ErGraphProcessor(File fBase)
	{
		this.fBase=fBase;
		
		mapNodes = new Hashtable<String,Node>();
		mapEdges = new Hashtable<String,Edge>();
		mapChilds = new Hashtable<String,Boolean>();
		graph = new Graph();
		graph.setNodes(new Nodes());
		graph.setEdges(new Edges());
	}
	
	public void addPackages(String sEjbPackage) throws IOException, ClassNotFoundException
	{
		addPackages(sEjbPackage,new ArrayList<String>());
	}
	public void addPackages(String sEjbPackage, List<String> subset) throws IOException, ClassNotFoundException
	{
		Set<String> setSub = new HashSet<String>();
		setSub.addAll(subset);
		
		File fPackage = new File(fBase,sEjbPackage);
		RecursiveFileFinder finder = new RecursiveFileFinder(FileFilterUtils.suffixFileFilter(".java"));
		List<File> list = finder.find(fPackage);
		for(File f : list)
		{
			createNode(f,setSub);
		}
		int i=0;
		for(String key : mapNodes.keySet())
		{
			Node n = mapNodes.get(key);
			n.setId(i);i++;
			n.setCode(null);
			mapNodes.put(key, n);
			graph.getNodes().getNode().add(n);
		}
		for(File f : list)
		{
			createEdge(f);
		}
	}
	
	public Graph create()
	{
		mergeEdges();
		return graph;
	}
	
	private void createNode(File f,Set<String> subSet) throws ClassNotFoundException
	{
		Class<?> c = ClassUtil.forFile(fBase, f);
		createNode(c,subSet);
	}
	
	private void createNode(Class<?> c,Set<String> subSet)
	{
		Annotation a = c.getAnnotation(EjbErNode.class);
		if(a!=null)
		{
			EjbErNode er = (EjbErNode)a;
			Node node = new Node();
			node.setCode(c.getName());
			node.setLabel(er.name());
			if(er.category().length()>0)
			{
				node.setCategory(er.category());
			}
			node.setSizeRelative(true);
			node.setSizeAdjustsColor(true);
			node.setSize(1-er.level());
			node.setType(""+er.level());
			
			boolean add = false;
			if(subSet.size()==0){add=true;}
			else if (er.subset().length()>0)
			{
				logger.trace("Subset available");
				String[] items = er.subset().split(",");
				for(String s : items)
				{
					if(subSet.contains(s)){add=true;}
				}
			}
			
			if(add){mapNodes.put(node.getCode(), node);}
		}
	}
	
	private void createEdge(File fClass) throws ClassNotFoundException
	{
		Class<?> c = ClassUtil.forFile(fBase, fClass);
		if(mapNodes.containsKey(c.getName()))
		{
			logger.trace("Processing edges for "+c.getName());
			Node source = mapNodes.get(c.getName());
			
			for(Field field : ReflectionUtil.toFields(c))
			{
				logger.trace("Field "+field.getName());
				Annotation annotations[] = field.getAnnotations();
				Cardinality cardinality = getCardinality(annotations);
				logger.trace("Cardinality: "+cardinality);
				if(cardinality!=null)
				{
					boolean targetIsChild = isTargetChildAnnotated(annotations);
					if(mapNodes.containsKey(field.getType().getName()))
					{
						Node target = mapNodes.get(field.getType().getName());
						createEdge(source, cardinality,target,targetIsChild);
					}
					else if(field.getType().getName().equals(List.class.getName()))
					{
						ParameterizedType pT = (ParameterizedType) field.getGenericType();
				        Class<?> gC = (Class<?>) pT.getActualTypeArguments()[0];
				     
				        if(mapNodes.containsKey(gC.getName()))
				        {
				        	Node target = mapNodes.get(gC.getName());
				        	createEdge(source, cardinality,target,targetIsChild);
				        }
					}
				}
			}
			
			Class<?> cSuper = c.getSuperclass();
			if(mapNodes.containsKey(cSuper.getName()))
			{
				Node target = mapNodes.get(cSuper.getName());
				createEdge(source, Cardinality.OneToOne,target,false);
				
			}			
		}
	}
	
	
	
	private void createEdge(Node source, Cardinality cardinality,Node target,boolean targetIsChild)
	{
		Edge e = new Edge();
		switch(cardinality)
		{
			case OneToOne:  e.setDirected(false);break;
			case OneToMany: e.setDirected(true);break;
			case ManyToOne: e.setDirected(true);break;
			case ManyToMany:e.setDirected(false);break;
		}
		
		e.setFrom(source.getId());
		e.setTo(target.getId());
		e.setType(cardinality.toString());
		String key = e.getFrom()+"-"+e.getTo();
		
		mapEdges.put(key, e);
		mapChilds.put(key, targetIsChild);
	}
	
	private Cardinality getCardinality(Annotation annotations[])
	{
		logger.trace("Annotation Length "+annotations.length);
		Cardinality cardinality = null;
		for (int j = 0; j < annotations.length; j++)
		{
			Annotation a = annotations[j];
			logger.trace(a.annotationType().getName());
			if(a.annotationType().getName().equals(javax.persistence.OneToOne.class.getName())){cardinality = Cardinality.OneToOne;}
			if(a.annotationType().getName().equals(javax.persistence.OneToMany.class.getName())){cardinality = Cardinality.OneToMany;}
			if(a.annotationType().getName().equals(javax.persistence.ManyToOne.class.getName())){cardinality = Cardinality.ManyToOne;}
			if(a.annotationType().getName().equals(javax.persistence.ManyToMany.class.getName())){cardinality = Cardinality.ManyToMany;}
			if(cardinality!=null){return cardinality;}
		}
		return cardinality;
	}
	
	private boolean isTargetChildAnnotated(Annotation annotations[])
	{
		boolean isChild = false;
		for (int j = 0; j < annotations.length; j++)
		{
			Annotation a = annotations[j];
			if(a.annotationType().getName().equals(net.sf.ahtutils.model.qualifier.EjbErNodeHierarchy.class.getName()))
			{
				isChild=true;
			}
		}
		return isChild;
	}
	
	private void mergeEdges()
	{
		Object[] keys = mapEdges.keySet().toArray();
		for(Object o : keys)
		{
			String keyF = (String)o;
			if(mapEdges.containsKey(keyF))
			{
				Edge eF = mapEdges.get(keyF);
				Cardinality cF = Cardinality.valueOf(eF.getType());
				String keyR = eF.getTo()+"-"+eF.getFrom();
				boolean isChildF = mapChilds.get(keyF);
				if(mapEdges.containsKey(keyR))
				{
					boolean isChildR = mapChilds.get(keyR);
					Edge eR = mapEdges.get(keyR);		
					
					Cardinality cR = Cardinality.valueOf(eR.getType());
					boolean rmF = false;
					boolean rmR = false;
					if(cF==Cardinality.OneToOne && cR == Cardinality.OneToOne){rmR=true;}
					else if(cF==Cardinality.ManyToMany && cR == Cardinality.ManyToMany){rmR=true;}
					else if(cF==Cardinality.OneToMany && cR == Cardinality.ManyToOne){rmR=true;}
					else if(cF==Cardinality.ManyToOne && cR == Cardinality.OneToMany){rmF=true;}
					
					if(rmF) {mapEdges.remove(keyF);}
					else if(rmR && !isChildR) {mapEdges.remove(keyR);}
				}
				else
				{
					if(!isChildF && cF==Cardinality.ManyToOne)
					{
						long from = eF.getFrom();
						long to = eF.getTo();
						eF.setTo(from);
						eF.setFrom(to);
						eF.setDirected(false);
					}
				}
			}
		}
		graph.getEdges().getEdge().addAll(mapEdges.values());
	}
}