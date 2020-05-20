package me.async.noores;
import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Map;
import java.util.List;

import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.java.JavaPluginLoader;

import me.async.noores.listeners.BlockBreakListener;
import me.async.noores.listeners.BlockPlaceListener;
import net.minecraft.server.v1_13_R2.BiomeBase;
import net.minecraft.server.v1_13_R2.Biomes;
import net.minecraft.server.v1_13_R2.Blocks;
import net.minecraft.server.v1_13_R2.DataConverterBiome;
import net.minecraft.server.v1_13_R2.WorldGenFeatureComposite;
import net.minecraft.server.v1_13_R2.WorldGenFeatureConfiguration;
import net.minecraft.server.v1_13_R2.WorldGenFeatureOreConfiguration;
import net.minecraft.server.v1_13_R2.WorldGenStage;

public class Main extends JavaPlugin {

	public Main() {
		
	}

	public Main(JavaPluginLoader loader, PluginDescriptionFile description, File dataFolder, File file) {
		super(loader, description, dataFolder, file);
	}

	
	@Override
	public void onEnable()
	{
		removeExpensiveOres();
		outputTemperatures();
		//this.getServer().getPluginManager().registerEvents(new ChunkPopulatorListener(), this);
		this.getServer().getPluginManager().registerEvents(new BlockBreakListener(), this);
		this.getServer().getPluginManager().registerEvents(new BlockPlaceListener(), this);
		
	}
	
	@Override
	public void onDisable()
	{
		
	}
	
	public void setDefaultBiomeMesa()
	{
		try {
			//Biomes.DEFAULT
			setFinalStatic(Biomes.class.getDeclaredField("b"), Biomes.BADLANDS);
		} catch(Throwable t) {
			
		}
	}
	
	private static void setFinalStatic(Field field, Object newValue) throws Exception {
	      field.setAccessible(true);

	      Field modifiersField = Field.class.getDeclaredField("modifiers");
	      modifiersField.setAccessible(true);
	      modifiersField.setInt(field, field.getModifiers() & ~Modifier.FINAL);

	      field.set(null, newValue);
	}
	
	public void outputTemperatures()
	{
		for(BiomeBase biome : BiomeBase.aG)
		{
			try {
				Field aN =  BiomeBase.class.getDeclaredField("aN"); //BiomeBase.aG.aN (temperature)
				aN.setAccessible(true);
				float temp = aN.getFloat(biome);
				System.out.println(biome.k() + ": " + temp);
				
				/*if(temp >= 2f)
				{
					aN.setFloat(biome, 100f);
				}*/
				DataConverterBiome.a.put("", "");

			} catch (Throwable t)
			{
				t.printStackTrace();
			}
		}
	}
	
	public void removeExpensiveOres()
	{
		for(BiomeBase biome : BiomeBase.aG)
		{
			try {
				Field aW =  BiomeBase.class.getDeclaredField("aW"); //BiomeBase.aG.aW (feature map)
				aW.setAccessible(true);
				Field b = WorldGenFeatureComposite.class.getDeclaredField("b");
				b.setAccessible(true);
				
				@SuppressWarnings("unchecked")
				Map<WorldGenStage.Decoration, List<WorldGenFeatureComposite<?, ?>>> featureMap = 
						(Map<WorldGenStage.Decoration, List<WorldGenFeatureComposite<?, ?>>>) aW.get(biome);
				
				//featureMap.remove(WorldGenStage.Decoration.UNDERGROUND_ORES);
				//featureMap.put(WorldGenStage.Decoration.UNDERGROUND_ORES, 
				//		new ArrayList<WorldGenFeatureComposite<?, ?>>());
				List<WorldGenFeatureComposite<?, ?>> featureList = featureMap.get(WorldGenStage.Decoration.UNDERGROUND_ORES);
				for(int i = 0; i < featureList.size(); i++)
				{
					WorldGenFeatureConfiguration unkconfig = (WorldGenFeatureConfiguration) b.get(featureList.get(i));
					if(unkconfig instanceof WorldGenFeatureOreConfiguration)
					{
						WorldGenFeatureOreConfiguration config = (WorldGenFeatureOreConfiguration)unkconfig;
						if(/*oreConfig.d == Blocks.COAL_ORE 
								|| */config.d.getBlock() == Blocks.IRON_ORE
								|| config.d.getBlock() == Blocks.GOLD_ORE
								|| config.d.getBlock() == Blocks.DIAMOND_ORE
								|| config.d.getBlock() == Blocks.EMERALD_ORE)
						{
							featureList.remove(i);
							i--;
						}
						else
						{
							//System.out.println(biome.k() + ": " + config.d.getBlock().toString());
						}
					}
				}
				
				featureMap.put(WorldGenStage.Decoration.UNDERGROUND_ORES, featureList);
				
				aW.set(biome, featureMap);
				
			} catch (Throwable t)
			{
				t.printStackTrace();
			}
		}
	}
}
