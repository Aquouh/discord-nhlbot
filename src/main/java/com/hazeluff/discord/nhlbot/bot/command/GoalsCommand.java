package com.hazeluff.discord.nhlbot.bot.command;

import com.hazeluff.discord.nhlbot.bot.NHLBot;
import com.hazeluff.discord.nhlbot.nhl.Game;
import com.hazeluff.discord.nhlbot.nhl.GameStatus;
import com.hazeluff.discord.nhlbot.nhl.Team;

import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IGuild;
import sx.blah.discord.handle.obj.IMessage;

/**
 * Displays all the goals in game of a 'Game Day Channel'
 */
public class GoalsCommand extends Command {

	public GoalsCommand(NHLBot nhlBot) {
		super(nhlBot);
	}

	@Override
	public void replyTo(IMessage message, String[] arguments) {
		IChannel channel = message.getChannel();
		IGuild guild = message.getGuild();
		Team preferredTeam;
		if (channel.isPrivate()) {
			nhlBot.getDiscordManager().sendMessage(channel, RUN_IN_SERVER_CHANNEL_MESSAGE);
		} else if ((preferredTeam = nhlBot.getPreferencesManager().getTeamByGuild(guild.getLongID())) == null) {
			nhlBot.getDiscordManager().sendMessage(channel, SUBSCRIBE_FIRST_MESSAGE);
		} else {
			Game game = nhlBot.getGameScheduler().getGameByChannelName(channel.getName());
			if (game == null) {
				nhlBot.getDiscordManager().sendMessage(channel, getRunInGameDayChannelMessage(guild, preferredTeam));
			} else if (game.getStatus() == GameStatus.PREVIEW) {
				nhlBot.getDiscordManager().sendMessage(channel, GAME_NOT_STARTED_MESSAGE);
			} else {
				nhlBot.getDiscordManager().sendMessage(channel,
						String.format("%s\n%s", game.getScoreMessage(), game.getGoalsMessage()));
			}
		}
	}

	@Override
	public boolean isAccept(String[] arguments) {
		return arguments[1].equalsIgnoreCase("goals");
	}

}
