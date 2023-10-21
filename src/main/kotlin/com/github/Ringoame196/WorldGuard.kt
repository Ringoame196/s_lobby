package com.github.Ringoame196

import com.sk89q.worldedit.bukkit.BukkitAdapter
import com.sk89q.worldguard.WorldGuard
import com.sk89q.worldguard.domains.DefaultDomain
import com.sk89q.worldguard.protection.managers.RegionManager
import com.sk89q.worldguard.protection.regions.ProtectedRegion
import org.bukkit.ChatColor
import org.bukkit.Location
import org.bukkit.World
import org.bukkit.entity.Player

class WorldGuard {
    fun getOwnerOfRegion(location: Location): DefaultDomain? {
        val worldGuard = WorldGuard.getInstance()
        val regionContainer = worldGuard.platform.regionContainer
        val regions = regionContainer.createQuery().getApplicableRegions(BukkitAdapter.adapt(location))

        // もし適用されるリージョンがない場合、nullを返す
        if (regions.size() == 0) {
            return null
        }

        // 最初のリージョンを取得
        val firstRegion: ProtectedRegion = regions.iterator().next()

        // リージョンのオーナーを返す
        return firstRegion.owners
    }
    fun getMemberOfRegion(location: Location): DefaultDomain? {
        val worldGuard = WorldGuard.getInstance()
        val regionContainer = worldGuard.platform.regionContainer
        val regions = regionContainer.createQuery().getApplicableRegions(BukkitAdapter.adapt(location))

        // もし適用されるリージョンがない場合、nullを返す
        if (regions.size() == 0) {
            return null
        }

        // 最初のリージョンを取得
        val firstRegion: ProtectedRegion = regions.iterator().next()

        // リージョンのオーナーを返す
        return firstRegion.members
    }
    fun getName(location: Location): String? {
        val worldGuard = WorldGuard.getInstance()
        val regionContainer = worldGuard.platform.regionContainer
        val regions = regionContainer.createQuery().getApplicableRegions(BukkitAdapter.adapt(location))

        // もし適用されるリージョンがない場合、nullを返す
        if (regions.size() == 0) {
            return null
        }

        // 最初のリージョンを取得
        val firstRegion: ProtectedRegion = regions.iterator().next()

        // リージョンのオーナーを返す
        return firstRegion.id.toString()
    }
    fun getOwner(world: World, regionName: String): String? {
        val worldGuard = WorldGuard.getInstance()
        val regionContainer = worldGuard.platform.regionContainer
        val regionManager: RegionManager? = regionContainer.get(BukkitAdapter.adapt(world))
        val region: ProtectedRegion? = regionManager?.getRegion(regionName)
        return region?.owners?.toPlayersString()
    }
    fun getMember(world: World, regionName: String): String? {
        val worldGuard = WorldGuard.getInstance()
        val regionContainer = worldGuard.platform.regionContainer
        val regionManager: RegionManager? = regionContainer.get(BukkitAdapter.adapt(world))
        val region: ProtectedRegion? = regionManager?.getRegion(regionName)
        return region?.members?.toPlayersString()
    }
    fun addOwnerToRegion(regionName: String, player: Player) {
        val newOwner = player.uniqueId
        val worldGuard = WorldGuard.getInstance()
        val regionContainer = worldGuard.platform.regionContainer
        val world = player.world
        val regionManager: RegionManager? = regionContainer.get(BukkitAdapter.adapt(world))
        val region: ProtectedRegion? = regionManager?.getRegion(regionName)
        if (region != null) {
            region.owners.addPlayer(newOwner)
            regionManager.save()
            player.sendMessage("${ChatColor.YELLOW}${regionName}のownerに追加されました")
        }
    }
    fun resetOwner(regionName: String, world: World) {
        val worldGuard = WorldGuard.getInstance()
        val regionContainer = worldGuard.platform.regionContainer
        val regionManager: RegionManager? = regionContainer.get(BukkitAdapter.adapt(world))
        val region: ProtectedRegion? = regionManager?.getRegion(regionName)

        if (region != null) {
            region.owners.removeAll()
            regionManager.save()
        }
    }
}